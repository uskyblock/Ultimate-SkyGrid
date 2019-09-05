package com.gmail.labuff.shane.UltimateSkyGrid;

import org.bukkit.*;
import org.bukkit.generator.*;
import java.util.*;

public class UltimateSkyGridGenerator extends ChunkGenerator
{
    public int worldHeight;
    
    public boolean canSpawn(final World world, final int x, final int z) {
        return true;
    }
    
    static void setBlock(final byte[][] result, final int x, final int y, final int z, final byte blkid) {
        if (result[y >> 4] == null) {
            result[y >> 4] = new byte[4096];
        }
        result[y >> 4][(y & 0xF) << 8 | z << 4 | x] = blkid;
    }
    
    public byte[][] generateBlockSections(final World world, final Random random, final int chunkX, final int chunkZ, final ChunkGenerator.BiomeGrid biomes) {
        if (world.getEnvironment() == World.Environment.NETHER) {
            this.worldHeight = UltimateSkyGrid.cNetherHeight;
        }
        else if (world.getEnvironment() == World.Environment.NORMAL) {
            this.worldHeight = UltimateSkyGrid.cHeight;
        }
        else {
            this.worldHeight = UltimateSkyGrid.cEndHeight;
        }
        final byte[][] result = new byte[this.worldHeight / 16][];
        if (UltimateSkyGrid.genGlass) {
            for (int x = 0; x < 16; ++x) {
                for (int y = 0; y < this.worldHeight; ++y) {
                    for (int z = 0; z < 16; ++z) {
                        if (x % 4 == 0 && y % 4 == 0 && z % 4 == 0) {
                            final int ID = this.getRandBlockID(world, random, UltimateSkyGrid.allBlocksOneWorld);
                            setBlock(result, x, y, z, (byte)ID);
                        }
                        else if (y < UltimateSkyGrid.cHeight - 3) {
                            final int ID = Material.GLASS.getId();
                            setBlock(result, x, y, z, (byte)ID);
                        }
                    }
                }
            }
        }
        else {
            for (int x = 0; x < 16; x += 4) {
                for (int y = 0; y < UltimateSkyGrid.cHeight; y += 4) {
                    for (int z = 0; z < 16; z += 4) {
                        final int ID = this.getRandBlockID(world, random, UltimateSkyGrid.allBlocksOneWorld);
                        setBlock(result, x, y, z, (byte)ID);
                    }
                }
            }
        }
        return result;
    }
    
    public Location getFixedSpawnLocation(final World world, final Random random) {
        return new Location(world, 0.5, (double)this.worldHeight, 0.5);
    }
    
    public List<BlockPopulator> getDefaultPopulators(final World world) {
        return Arrays.asList(new UltimateSkyGridPopulator());
    }
    
    public int getRandBlockID(final World world, final Random random, final boolean allBlocks) {
        if (world.getEnvironment() == World.Environment.NORMAL) {
            if (allBlocks) {
                final int r = random.nextInt(10000);
                if (r < UltimateSkyGrid.cMythic) {
                    final int randID = UltimateSkyGrid.iMythic[random.nextInt(UltimateSkyGrid.iMythic.length)];
                    return randID;
                }
                if (r < UltimateSkyGrid.cUnique) {
                    final int randID = UltimateSkyGrid.iUnique[random.nextInt(UltimateSkyGrid.iUnique.length)];
                    return randID;
                }
                if (r < UltimateSkyGrid.cRare) {
                    final int randID = UltimateSkyGrid.iRare[random.nextInt(UltimateSkyGrid.iRare.length)];
                    return randID;
                }
                if (r < UltimateSkyGrid.cUncommon) {
                    final int randID = UltimateSkyGrid.iUncommon[random.nextInt(UltimateSkyGrid.iUncommon.length)];
                    return randID;
                }
                final int randID = UltimateSkyGrid.iAbundant[random.nextInt(UltimateSkyGrid.iAbundant.length)];
                return randID;
            }
            else {
                final int r = random.nextInt(10000);
                if (r < UltimateSkyGrid.cMythic) {
                    final int randID = UltimateSkyGrid.iNormMythic[random.nextInt(UltimateSkyGrid.iNormMythic.length)];
                    return randID;
                }
                if (r < UltimateSkyGrid.cUnique) {
                    final int randID = UltimateSkyGrid.iNormUnique[random.nextInt(UltimateSkyGrid.iNormUnique.length)];
                    return randID;
                }
                if (r < UltimateSkyGrid.cRare) {
                    final int randID = UltimateSkyGrid.iNormRare[random.nextInt(UltimateSkyGrid.iNormRare.length)];
                    return randID;
                }
                if (r < UltimateSkyGrid.cUncommon) {
                    final int randID = UltimateSkyGrid.iNormUncommon[random.nextInt(UltimateSkyGrid.iNormUncommon.length)];
                    return randID;
                }
                final int randID = UltimateSkyGrid.iNormAbundant[random.nextInt(UltimateSkyGrid.iNormAbundant.length)];
                return randID;
            }
        }
        else if (world.getEnvironment() == World.Environment.NETHER) {
            final int r = random.nextInt(100);
            if (r < UltimateSkyGrid.cNethRare) {
                final int randID = UltimateSkyGrid.iNethBlkRare[random.nextInt(UltimateSkyGrid.iNethBlkRare.length)];
                return randID;
            }
            final int randID = UltimateSkyGrid.iNethBlkNorm[random.nextInt(UltimateSkyGrid.iNethBlkNorm.length)];
            return randID;
        }
        else {
            final int r = random.nextInt(100);
            if (r < UltimateSkyGrid.cEndRare) {
                final int randID = UltimateSkyGrid.iEndBlkRare[random.nextInt(UltimateSkyGrid.iEndBlkRare.length)];
                return randID;
            }
            final int randID = UltimateSkyGrid.iEndBlkNorm[random.nextInt(UltimateSkyGrid.iEndBlkNorm.length)];
            return randID;
        }
    }
}
