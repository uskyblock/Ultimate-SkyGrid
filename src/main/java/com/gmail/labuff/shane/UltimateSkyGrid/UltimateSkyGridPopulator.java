package com.gmail.labuff.shane.UltimateSkyGrid;

import org.bukkit.generator.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.inventory.*;

public class UltimateSkyGridPopulator extends BlockPopulator
{
    int[] spawnEgg;
    
    public UltimateSkyGridPopulator() {
        this.spawnEgg = new int[] { 61, 56, 62, 50, 58, 54, 51, 52, 57, 55, 90, 91, 93, 92, 96, 95, 120, 59, 98, 66 };
    }
    
    public void populate(final World world, final Random random, final Chunk chunk) {
        final int wH = world.getMaxHeight();
        for (int x = 0; x < 16; x += 4) {
            for (int y = 0; y < wH; y += 4) {
                for (int z = 0; z < 16; z += 4) {
                    final Block blk = chunk.getBlock(x, y, z);
                    if (blk.getType() == Material.CHEST) {
                        final Chest chest = (Chest)blk.getState();
                        this.populateChest(world, random, UltimateSkyGrid.allBlocksOneWorld, chest);
                    }
                    else if (blk.getType() == Material.GRASS) {
                        blk.getRelative(BlockFace.UP).setType(getGrassPop(), false);
                        if (blk.getRelative(BlockFace.UP).getType() == Material.RED_ROSE) {
                            final Random r = new Random();
                            blk.getRelative(BlockFace.UP).setData((byte)r.nextInt(9));
                        }
                    }
                    else if (blk.getType() == Material.DIRT) {
                        final Random r = new Random();
                        final int data = r.nextInt(3);
                        blk.setData((byte)data);
                        if (data == 0) {
                            blk.getRelative(BlockFace.UP).setType(getDirtPop());
                            if (blk.getRelative(BlockFace.UP).getType() == Material.SAPLING) {
                                blk.getRelative(BlockFace.UP).setData((byte)r.nextInt(6));
                            }
                        }
                    }
                    else if (blk.getType() == Material.WOOL) {
                        final Random rnd = new Random();
                        blk.setData((byte)rnd.nextInt(16));
                    }
                    else if (blk.getType() == Material.PRISMARINE) {
                        final Random rnd = new Random();
                        blk.setData((byte)rnd.nextInt(3));
                    }
                    else if (blk.getType() == Material.RED_SANDSTONE) {
                        final Random rnd = new Random();
                        blk.setData((byte)rnd.nextInt(3));
                    }
                    else if (blk.getType() == Material.SMOOTH_BRICK) {
                        final Random rnd = new Random();
                        blk.setData((byte)rnd.nextInt(4));
                    }
                    else if (blk.getType() == Material.STONE) {
                        final Random rnd = new Random();
                        blk.setData((byte)rnd.nextInt(7));
                    }
                    else if (blk.getType() == Material.LOG) {
                        final Random rnd = new Random();
                        blk.setData((byte)rnd.nextInt(4));
                    }
                    else if (blk.getType() == Material.LOG_2) {
                        final Random rnd = new Random();
                        blk.setData((byte)rnd.nextInt(2));
                    }
                    else if (blk.getType() == Material.WOOD) {
                        final Random rnd = new Random();
                        blk.setData((byte)rnd.nextInt(6));
                    }
                    else if (blk.getType() == Material.SAND) {
                        final Random r = new Random();
                        final int data = r.nextInt(2);
                        blk.setData((byte)data);
                        if (data == 0 && r.nextInt(10) < 1) {
                            blk.getRelative(BlockFace.UP).setTypeId(Material.CACTUS.getId(), false);
                        }
                    }
                    else if (blk.getType() == Material.SOUL_SAND) {
                        blk.getRelative(BlockFace.UP).setType(getSoulPop());
                    }
                    else if (blk.getType() == Material.MOB_SPAWNER) {
                        if (world.getEnvironment() == World.Environment.NETHER || world.getEnvironment() == World.Environment.THE_END) {
                            if (world.getEnvironment() == World.Environment.NETHER) {
                                final CreatureSpawner spawner = (CreatureSpawner)blk.getState();
                                spawner.setSpawnedType(this.getNetherEntity());
                            }
                            else {
                                final CreatureSpawner spawner = (CreatureSpawner)blk.getState();
                                spawner.setSpawnedType(EntityType.ENDERMAN);
                            }
                        }
                        else if (!UltimateSkyGrid.allBlocksOneWorld) {
                            final CreatureSpawner spawner = (CreatureSpawner)blk.getState();
                            spawner.setSpawnedType(this.getNormEntity());
                        }
                        else {
                            final CreatureSpawner spawner = (CreatureSpawner)blk.getState();
                            spawner.setSpawnedType(getEntityType());
                        }
                    }
                }
            }
        }
    }
    
    public static EntityType getEntityType() {
        final EntityType[] mobHosNorm = { EntityType.ZOMBIE, EntityType.SKELETON, EntityType.SPIDER, EntityType.PIG_ZOMBIE, EntityType.SLIME };
        final EntityType[] mobHosRare = { EntityType.BLAZE, EntityType.GHAST, EntityType.MAGMA_CUBE, EntityType.CREEPER, EntityType.ENDERMAN };
        final EntityType[] mobNorm = { EntityType.PIG, EntityType.SHEEP, EntityType.CHICKEN };
        final EntityType[] mobRare = { EntityType.COW, EntityType.MUSHROOM_COW };
        final Random random = new Random();
        final int c = random.nextInt(100);
        EntityType entRet;
        if (c < 2) {
            entRet = mobHosRare[random.nextInt(mobHosRare.length)];
        }
        else if (c < 5) {
            entRet = mobRare[random.nextInt(mobRare.length)];
        }
        else if (c < 14) {
            entRet = mobHosNorm[random.nextInt(mobHosNorm.length)];
        }
        else {
            entRet = mobNorm[random.nextInt(mobNorm.length)];
        }
        return entRet;
    }
    
    public EntityType getNormEntity() {
        final EntityType[] mobHosNorm = { EntityType.ZOMBIE, EntityType.SKELETON, EntityType.SPIDER, EntityType.SLIME };
        final EntityType[] mobHosRare = { EntityType.CREEPER, EntityType.ENDERMAN };
        final EntityType[] mobNorm = { EntityType.PIG, EntityType.SHEEP, EntityType.CHICKEN };
        final EntityType[] mobRare = { EntityType.COW, EntityType.MUSHROOM_COW };
        final Random random = new Random();
        final int c = random.nextInt(100);
        EntityType ent;
        if (c < 2) {
            ent = mobHosRare[random.nextInt(mobHosRare.length)];
        }
        else if (c < 5) {
            ent = mobRare[random.nextInt(mobRare.length)];
        }
        else if (c < 14) {
            ent = mobHosNorm[random.nextInt(mobHosNorm.length)];
        }
        else {
            ent = mobNorm[random.nextInt(mobNorm.length)];
        }
        return ent;
    }
    
    public EntityType getNetherEntity() {
        final EntityType[] mobHosNorm = { EntityType.PIG_ZOMBIE, EntityType.SKELETON };
        final EntityType[] mobHosRare = { EntityType.BLAZE, EntityType.GHAST, EntityType.MAGMA_CUBE };
        final Random random = new Random();
        final int c = random.nextInt(100);
        EntityType ent;
        if (c < 2) {
            ent = mobHosRare[random.nextInt(mobHosRare.length)];
        }
        else {
            ent = mobHosNorm[random.nextInt(mobHosNorm.length)];
        }
        return ent;
    }
    
    public static Material getGrassPop() {
        final Random rand = new Random();
        final int p = rand.nextInt(100);
        if (p < 5) {
            final Material popMat = Material.RED_MUSHROOM;
            return popMat;
        }
        if (p < 10) {
            final Material popMat = Material.BROWN_MUSHROOM;
            return popMat;
        }
        if (p < 18) {
            final Material popMat = Material.RED_ROSE;
            return popMat;
        }
        if (p < 20) {
            final Material popMat = Material.YELLOW_FLOWER;
            return popMat;
        }
        if (p < 25) {
            final Material popMat = Material.SUGAR_CANE_BLOCK;
            return popMat;
        }
        return Material.AIR;
    }
    
    public static Material getSoulPop() {
        final Random random = new Random();
        final int a = random.nextInt(10);
        if (a < 2) {
            return Material.NETHER_WARTS;
        }
        return Material.AIR;
    }
    
    public static Material getDirtPop() {
        final Random r = new Random();
        final int p = r.nextInt(10);
        if (p < 1) {
            return Material.SAPLING;
        }
        return Material.AIR;
    }
    
    public void populateChest(final World world, final Random random, final boolean allBlocks, final Chest chest) {
        final World.Environment env = world.getEnvironment();
        int[] itemMythicID = { 0 };
        int[] itemMythicAmount = { 0 };
        int mythChance = 0;
        int rareChance;
        int[] itemRareID;
        int[] itemRareAmount;
        int[] itemID;
        int[] itemAmount;
        int maxI;
        if (env == World.Environment.NETHER || env == World.Environment.THE_END) {
            if (env == World.Environment.NETHER) {
                rareChance = UltimateSkyGrid.cNethChRare;
                itemRareID = UltimateSkyGrid.iNethChRare;
                itemRareAmount = UltimateSkyGrid.iNethChRareAmount;
                itemID = UltimateSkyGrid.iNethChNorm;
                itemAmount = UltimateSkyGrid.iNethChNormAmount;
                final int a = random.nextInt(10);
                final int preMax1 = random.nextInt(2);
                final int preMax2 = random.nextInt(5);
                if (a < 2) {
                    maxI = 1 + preMax2;
                }
                else {
                    maxI = 1 + preMax1;
                }
            }
            else {
                rareChance = UltimateSkyGrid.cEndChRare;
                itemRareID = UltimateSkyGrid.iEndChRare;
                itemRareAmount = UltimateSkyGrid.iEndChRareAmount;
                itemID = UltimateSkyGrid.iEndChNorm;
                itemAmount = UltimateSkyGrid.iEndChNormAmount;
                final int a = random.nextInt(10);
                final int preMax1 = random.nextInt(2);
                final int preMax2 = random.nextInt(5);
                if (a < 2) {
                    maxI = 1 + preMax2;
                }
                else {
                    maxI = 1 + preMax1;
                }
            }
        }
        else if (allBlocks) {
            mythChance = UltimateSkyGrid.cChMythic;
            rareChance = UltimateSkyGrid.cChRare;
            itemMythicID = UltimateSkyGrid.iChMythic;
            itemMythicAmount = UltimateSkyGrid.iChMythicAmount;
            itemRareID = UltimateSkyGrid.iChRare;
            itemRareAmount = UltimateSkyGrid.iChRareAmount;
            itemID = UltimateSkyGrid.iChNormal;
            itemAmount = UltimateSkyGrid.iChNormalAmount;
            final int a = random.nextInt(10);
            final int preMax1 = random.nextInt(4);
            final int preMax2 = random.nextInt(10);
            if (a < 2) {
                maxI = 1 + preMax2;
            }
            else {
                maxI = 1 + preMax1;
            }
        }
        else {
            mythChance = UltimateSkyGrid.cChMythic;
            rareChance = UltimateSkyGrid.cChRare;
            itemMythicID = UltimateSkyGrid.iNormChMythic;
            itemMythicAmount = UltimateSkyGrid.iNormChMythicAmount;
            itemRareID = UltimateSkyGrid.iNormChRare;
            itemRareAmount = UltimateSkyGrid.iNormChRareAmount;
            itemID = UltimateSkyGrid.iNormChNormal;
            itemAmount = UltimateSkyGrid.iNormChNormalAmount;
            final int a = random.nextInt(10);
            final int preMax1 = random.nextInt(4);
            final int preMax2 = random.nextInt(10);
            if (a < 2) {
                maxI = 1 + preMax2;
            }
            else {
                maxI = 1 + preMax1;
            }
        }
        for (int i = 0; i < maxI; ++i) {
            final Inventory inv = chest.getInventory();
            final int quality = random.nextInt(100);
            if (quality < rareChance) {
                if (quality < mythChance && env == World.Environment.NORMAL) {
                    final int aPos = random.nextInt(itemMythicID.length);
                    final int ID = itemMythicID[aPos];
                    final int maxAmount = itemMythicAmount[aPos];
                    int amount;
                    if (maxAmount == 1) {
                        amount = 1;
                    }
                    else {
                        amount = random.nextInt(maxAmount) + 1;
                    }
                    ItemStack itm = new ItemStack(ID, amount, (short)0);
                    if (itm.getType() == Material.MONSTER_EGG) {
                        final Random rdm = new Random();
                        itm = new ItemStack(Material.MONSTER_EGG, 1, (short)this.spawnEgg[rdm.nextInt(this.spawnEgg.length)]);
                        inv.addItem(new ItemStack[] { itm });
                    }
                    else {
                        inv.addItem(new ItemStack[] { itm });
                    }
                }
                else {
                    final int aPos = random.nextInt(itemRareID.length);
                    final int ID = itemRareID[aPos];
                    final int maxAmount = itemRareAmount[aPos];
                    int amount;
                    if (maxAmount == 1) {
                        amount = 1;
                    }
                    else {
                        amount = random.nextInt(maxAmount) + 1;
                    }
                    ItemStack itm = new ItemStack(ID, amount, (short)0);
                    if (itm.getType() == Material.MONSTER_EGG || itm.getType() == Material.LOG || itm.getType() == Material.LOG_2) {
                        if (itm.getType() == Material.MONSTER_EGG) {
                            itm = new ItemStack(Material.MONSTER_EGG, amount, (short)this.spawnEgg[random.nextInt(this.spawnEgg.length)]);
                            inv.addItem(new ItemStack[] { itm });
                        }
                        else if (itm.getType() == Material.LOG) {
                            itm = new ItemStack(Material.LOG, amount, (short)random.nextInt(4));
                            inv.addItem(new ItemStack[] { itm });
                        }
                        else if (itm.getType() == Material.LOG_2) {
                            itm = new ItemStack(Material.LOG_2, amount, (short)random.nextInt(2));
                            inv.addItem(new ItemStack[] { itm });
                        }
                    }
                    else {
                        inv.addItem(new ItemStack[] { itm });
                    }
                }
            }
            else {
                final int aPos = random.nextInt(itemID.length);
                final int ID = itemID[aPos];
                final int maxAmount = itemAmount[aPos];
                int amount;
                if (maxAmount == 1) {
                    amount = 1;
                }
                else {
                    amount = random.nextInt(maxAmount) + 1;
                }
                ItemStack itm = new ItemStack(ID, amount, (short)0);
                if (itm.getType() == Material.MONSTER_EGG || itm.getType() == Material.LOG) {
                    if (itm.getType() == Material.MONSTER_EGG) {
                        itm = new ItemStack(Material.MONSTER_EGG, amount, (short)this.spawnEgg[random.nextInt(this.spawnEgg.length)]);
                        inv.addItem(new ItemStack[] { itm });
                    }
                    else if (itm.getType() == Material.LOG) {
                        itm = new ItemStack(Material.LOG, amount, (short)random.nextInt(4));
                        inv.addItem(new ItemStack[] { itm });
                    }
                    else if (itm.getType() == Material.LOG_2) {
                        itm = new ItemStack(Material.LOG_2, amount, (short)random.nextInt(2));
                        inv.addItem(new ItemStack[] { itm });
                    }
                }
                else {
                    inv.addItem(new ItemStack[] { itm });
                }
            }
        }
    }
}
