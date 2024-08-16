package tech.samgosden.magestones.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import tech.samgosden.magestones.blocks.MageCrystalBlock;
import tech.samgosden.magestones.blocks.MageCrystalBlockEntity;

import java.util.Objects;


public class MageCrystalItem extends BlockItem {
    public MageCrystalItem(Settings settings, Block block) {
        super(block, settings);
    }

    @Override
    public ActionResult place(ItemPlacementContext context) {
        if (!this.getBlock().isEnabled(context.getWorld().getEnabledFeatures())) {
            return ActionResult.FAIL;
        } else if (!context.canPlace()) {
            return ActionResult.FAIL;
        } else {
            ItemPlacementContext itemPlacementContext = this.getPlacementContext(context);
            if (itemPlacementContext == null) {
                return ActionResult.FAIL;
            } else {
                BlockState blockState = this.getPlacementState(itemPlacementContext);
                System.out.println("The First");
                if (blockState == null) {
                    return ActionResult.FAIL;
                } else if (!this.place(itemPlacementContext, blockState)) {
                    return ActionResult.FAIL;
                } else {
                    BlockPos blockPos = itemPlacementContext.getBlockPos();
                    World world = itemPlacementContext.getWorld();
                    PlayerEntity playerEntity = itemPlacementContext.getPlayer();
                    ItemStack itemStack = itemPlacementContext.getStack();
                    BlockState blockState2 = world.getBlockState(blockPos);
                    if (blockState2.isOf(blockState.getBlock())) {
                        System.out.println("The second");
                        blockState2 = this.placeFromNbt(blockPos, world, itemStack, blockState2);
                        this.postPlacement(blockPos, world, playerEntity, itemStack, blockState2);
                        blockState2.getBlock().onPlaced(world, blockPos, blockState2, playerEntity, itemStack);
                        if (playerEntity instanceof ServerPlayerEntity) {
                            Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos, itemStack);
                        }
                    }

                    BlockSoundGroup blockSoundGroup = blockState2.getSoundGroup();
                    world.playSound(
                            playerEntity,
                            blockPos,
                            this.getPlaceSound(blockState2),
                            SoundCategory.BLOCKS,
                            (blockSoundGroup.getVolume() + 1.0F) / 2.0F,
                            blockSoundGroup.getPitch() * 0.8F
                    );
                    world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Emitter.of(playerEntity, blockState2));
                    if (playerEntity == null || !playerEntity.getAbilities().creativeMode) {
                        itemStack.decrement(1);
                    }

                    return ActionResult.success(world.isClient);
                }
            }
        }
    }

    @Override
    protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        return writeNbtToBlockEntity(world, player, pos, stack);
    }
    private BlockState placeFromNbt(BlockPos pos, World world, ItemStack stack, BlockState state) {
        BlockState blockState = state;
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound != null) {
            NbtCompound nbtCompound2 = nbtCompound.getCompound("BlockStateTag");
            StateManager<Block, BlockState> stateManager = state.getBlock().getStateManager();

            for (String string : nbtCompound2.getKeys()) {
                Property<?> property = stateManager.getProperty(string);
                if (property != null) {
                    String string2 = Objects.requireNonNull(nbtCompound2.get(string)).asString();
                    blockState = with(blockState, property, string2);
                }
            }
        }

        if (blockState != state) {
            world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
        }

        return blockState;
    }

    private static <T extends Comparable<T>> BlockState with(BlockState state, Property<T> property, String name) {
        return property.parse(name).map(value -> state.with(property, value)).orElse(state);
    }
    public static boolean writeNbtToBlockEntity(World world, @Nullable PlayerEntity player, BlockPos pos, ItemStack stack) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            NbtCompound nbt = stack.getNbt();
            if (blockEntity instanceof MageCrystalBlockEntity mageCrystalBlockEntity && nbt != null) {
                if (nbt.contains("durability")) {
                    int durability = nbt.getInt("durability");
                    mageCrystalBlockEntity.setTicksLeft(durability);
                    if (durability == 0) {
                        world.setBlockState(pos, world.getBlockState(pos).with(MageCrystalBlock.ISACTIVE, false));
                    }
                }
            }
            MinecraftServer minecraftServer = world.getServer();
                if (minecraftServer != null) {
                    NbtCompound nbtCompound = getBlockEntityNbt(stack);
                    if (nbtCompound != null) {
                        if (!world.isClient && blockEntity.copyItemDataRequiresOperator() && (player == null || !player.isCreativeLevelTwoOp())) {
                            return false;
                        }
                        NbtCompound nbtCompound2 = blockEntity.createNbt();
                        NbtCompound nbtCompound3 = nbtCompound2.copy();
                        nbtCompound2.copyFrom(nbtCompound);
                        if (!nbtCompound2.equals(nbtCompound3)) {
                            blockEntity.readNbt(nbtCompound2);
                            blockEntity.markDirty();
                            return true;
                        }
                }
            }
        }
        return false;
    }


    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        if (stack.getNbt() != null) {
            return stack.hasNbt() && stack.getNbt().contains("durability");
        }
        else {
            return false;
        }
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        if (stack.hasNbt() && (stack.getNbt() != null && stack.getNbt().contains("durability"))) {
            int durability = stack.getNbt().getInt("durability");
            int maxDurability = stack.getMaxDamage(); // Replace with your max durability
            return Math.round(13.0F - (float) (maxDurability-durability) * 13.0F / (float) maxDurability);
        }
        return super.getItemBarStep(stack);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return MathHelper.hsvToRgb(Math.max(0.0F, (float) stack.getDamage() / (float) stack.getMaxDamage()) / 3.0F, 1.0F, 1.0F);
    }

}
