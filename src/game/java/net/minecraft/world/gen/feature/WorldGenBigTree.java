package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import java.util.List;
import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**+
 * This portion of EaglercraftX contains deobfuscated Minecraft 1.8 source code.
 * 
 * Minecraft 1.8.8 bytecode is (c) 2015 Mojang AB. "Do not distribute!"
 * Mod Coder Pack v9.18 deobfuscation configs are (c) Copyright by the MCP Team
 * 
 * EaglercraftX 1.8 patch files (c) 2022-2024 lax1dude, ayunami2000. All Rights Reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 */
public class WorldGenBigTree extends WorldGenAbstractTree {
	private EaglercraftRandom rand;
	private World world;
	private BlockPos basePos = BlockPos.ORIGIN;
	int heightLimit;
	int height;
	double heightAttenuation = 0.618D;
	double branchSlope = 0.381D;
	double scaleWidth = 1.0D;
	double leafDensity = 1.0D;
	int trunkSize = 1;
	int heightLimitLimit = 12;
	int leafDistanceLimit = 4;
	List<WorldGenBigTree.FoliageCoordinates> field_175948_j;

	public WorldGenBigTree(boolean parFlag) {
		super(parFlag);
	}

	/**+
	 * Generates a list of leaf nodes for the tree, to be populated
	 * by generateLeaves.
	 */
	void generateLeafNodeList() {
		this.height = (int) ((double) this.heightLimit * this.heightAttenuation);
		if (this.height >= this.heightLimit) {
			this.height = this.heightLimit - 1;
		}

		int i = (int) (1.382D + Math.pow(this.leafDensity * (double) this.heightLimit / 13.0D, 2.0D));
		if (i < 1) {
			i = 1;
		}

		int j = this.basePos.getY() + this.height;
		int k = this.heightLimit - this.leafDistanceLimit;
		this.field_175948_j = Lists.newArrayList();
		this.field_175948_j.add(new WorldGenBigTree.FoliageCoordinates(this.basePos.up(k), j));

		for (; k >= 0; --k) {
			float f = this.layerSize(k);
			if (f >= 0.0F) {
				for (int l = 0; l < i; ++l) {
					double d0 = this.scaleWidth * (double) f * ((double) rand.nextFloat() + 0.328D);
					double d1 = (double) (rand.nextFloat() * 2.0F) * 3.141592653589793D;
					double d2 = d0 * Math.sin(d1) + 0.5D;
					double d3 = d0 * Math.cos(d1) + 0.5D;
					BlockPos blockpos = this.basePos.add(d2, (double) (k - 1), d3);
					BlockPos blockpos1 = blockpos.up(this.leafDistanceLimit);
					if (this.checkBlockLine(blockpos, blockpos1) == -1) {
						int i1 = this.basePos.getX() - blockpos.getX();
						int j1 = this.basePos.getZ() - blockpos.getZ();
						double d4 = (double) blockpos.getY()
								- Math.sqrt((double) (i1 * i1 + j1 * j1)) * this.branchSlope;
						int k1 = d4 > (double) j ? j : (int) d4;
						BlockPos blockpos2 = new BlockPos(this.basePos.getX(), k1, this.basePos.getZ());
						if (this.checkBlockLine(blockpos2, blockpos) == -1) {
							this.field_175948_j.add(new WorldGenBigTree.FoliageCoordinates(blockpos, blockpos2.getY()));
						}
					}
				}
			}
		}

	}

	void func_181631_a(BlockPos parBlockPos, float parFloat1, IBlockState parIBlockState) {
		int i = (int) ((double) parFloat1 + 0.618D);

		for (int j = -i; j <= i; ++j) {
			for (int k = -i; k <= i; ++k) {
				if (Math.pow((double) Math.abs(j) + 0.5D, 2.0D)
						+ Math.pow((double) Math.abs(k) + 0.5D, 2.0D) <= (double) (parFloat1 * parFloat1)) {
					BlockPos blockpos = parBlockPos.add(j, 0, k);
					Material material = this.world.getBlockState(blockpos).getBlock().getMaterial();
					if (material == Material.air || material == Material.leaves) {
						this.setBlockAndNotifyAdequately(this.world, blockpos, parIBlockState);
					}
				}
			}
		}

	}

	/**+
	 * Gets the rough size of a layer of the tree.
	 */
	float layerSize(int parInt1) {
		if ((float) parInt1 < (float) this.heightLimit * 0.3F) {
			return -1.0F;
		} else {
			float f = (float) this.heightLimit / 2.0F;
			float f1 = f - (float) parInt1;
			float f2 = MathHelper.sqrt_float(f * f - f1 * f1);
			if (f1 == 0.0F) {
				f2 = f;
			} else if (Math.abs(f1) >= f) {
				return 0.0F;
			}

			return f2 * 0.5F;
		}
	}

	float leafSize(int parInt1) {
		return parInt1 >= 0 && parInt1 < this.leafDistanceLimit
				? (parInt1 != 0 && parInt1 != this.leafDistanceLimit - 1 ? 3.0F : 2.0F)
				: -1.0F;
	}

	/**+
	 * Generates the leaves surrounding an individual entry in the
	 * leafNodes list.
	 */
	void generateLeafNode(BlockPos pos) {
		for (int i = 0; i < this.leafDistanceLimit; ++i) {
			this.func_181631_a(pos.up(i), this.leafSize(i),
					Blocks.leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false)));
		}

	}

	void func_175937_a(BlockPos parBlockPos, BlockPos parBlockPos2, Block parBlock) {
		BlockPos blockpos = parBlockPos2.add(-parBlockPos.getX(), -parBlockPos.getY(), -parBlockPos.getZ());
		int i = this.getGreatestDistance(blockpos);
		float f = (float) blockpos.getX() / (float) i;
		float f1 = (float) blockpos.getY() / (float) i;
		float f2 = (float) blockpos.getZ() / (float) i;

		for (int j = 0; j <= i; ++j) {
			BlockPos blockpos1 = parBlockPos.add((double) (0.5F + (float) j * f), (double) (0.5F + (float) j * f1),
					(double) (0.5F + (float) j * f2));
			BlockLog.EnumAxis blocklog$enumaxis = this.func_175938_b(parBlockPos, blockpos1);
			this.setBlockAndNotifyAdequately(this.world, blockpos1,
					parBlock.getDefaultState().withProperty(BlockLog.LOG_AXIS, blocklog$enumaxis));
		}

	}

	/**+
	 * Returns the absolute greatest distance in the BlockPos
	 * object.
	 */
	private int getGreatestDistance(BlockPos posIn) {
		int i = MathHelper.abs_int(posIn.getX());
		int j = MathHelper.abs_int(posIn.getY());
		int k = MathHelper.abs_int(posIn.getZ());
		return k > i && k > j ? k : (j > i ? j : i);
	}

	private BlockLog.EnumAxis func_175938_b(BlockPos parBlockPos, BlockPos parBlockPos2) {
		BlockLog.EnumAxis blocklog$enumaxis = BlockLog.EnumAxis.Y;
		int i = Math.abs(parBlockPos2.getX() - parBlockPos.getX());
		int j = Math.abs(parBlockPos2.getZ() - parBlockPos.getZ());
		int k = Math.max(i, j);
		if (k > 0) {
			if (i == k) {
				blocklog$enumaxis = BlockLog.EnumAxis.X;
			} else if (j == k) {
				blocklog$enumaxis = BlockLog.EnumAxis.Z;
			}
		}

		return blocklog$enumaxis;
	}

	/**+
	 * Generates the leaf portion of the tree as specified by the
	 * leafNodes list.
	 */
	void generateLeaves() {
		for (int i = 0, l = this.field_175948_j.size(); i < l; ++i) {
			this.generateLeafNode(this.field_175948_j.get(i));
		}

	}

	/**+
	 * Indicates whether or not a leaf node requires additional wood
	 * to be added to preserve integrity.
	 */
	boolean leafNodeNeedsBase(int parInt1) {
		return (double) parInt1 >= (double) this.heightLimit * 0.2D;
	}

	/**+
	 * Places the trunk for the big tree that is being generated.
	 * Able to generate double-sized trunks by changing a field that
	 * is always 1 to 2.
	 */
	void generateTrunk() {
		BlockPos blockpos = this.basePos;
		BlockPos blockpos1 = this.basePos.up(this.height);
		Block block = Blocks.log;
		this.func_175937_a(blockpos, blockpos1, block);
		if (this.trunkSize == 2) {
			this.func_175937_a(blockpos.east(), blockpos1.east(), block);
			this.func_175937_a(blockpos.east().south(), blockpos1.east().south(), block);
			this.func_175937_a(blockpos.south(), blockpos1.south(), block);
		}

	}

	/**+
	 * Generates additional wood blocks to fill out the bases of
	 * different leaf nodes that would otherwise degrade.
	 */
	void generateLeafNodeBases() {
		for (int j = 0, l = this.field_175948_j.size(); j < l; ++j) {
			WorldGenBigTree.FoliageCoordinates worldgenbigtree$foliagecoordinates = this.field_175948_j.get(j);
			int i = worldgenbigtree$foliagecoordinates.func_177999_q();
			BlockPos blockpos = new BlockPos(this.basePos.getX(), i, this.basePos.getZ());
			if (!blockpos.equals(worldgenbigtree$foliagecoordinates)
					&& this.leafNodeNeedsBase(i - this.basePos.getY())) {
				this.func_175937_a(blockpos, worldgenbigtree$foliagecoordinates, Blocks.log);
			}
		}

	}

	/**+
	 * Checks a line of blocks in the world from the first
	 * coordinate to triplet to the second, returning the distance
	 * (in blocks) before a non-air, non-leaf block is encountered
	 * and/or the end is encountered.
	 */
	int checkBlockLine(BlockPos posOne, BlockPos posTwo) {
		BlockPos blockpos = posTwo.add(-posOne.getX(), -posOne.getY(), -posOne.getZ());
		int i = this.getGreatestDistance(blockpos);
		float f = (float) blockpos.getX() / (float) i;
		float f1 = (float) blockpos.getY() / (float) i;
		float f2 = (float) blockpos.getZ() / (float) i;
		if (i == 0) {
			return -1;
		} else {
			for (int j = 0; j <= i; ++j) {
				BlockPos blockpos1 = posOne.add((double) (0.5F + (float) j * f), (double) (0.5F + (float) j * f1),
						(double) (0.5F + (float) j * f2));
				if (!this.func_150523_a(this.world.getBlockState(blockpos1).getBlock())) {
					return j;
				}
			}

			return -1;
		}
	}

	public void func_175904_e() {
		this.leafDistanceLimit = 5;
	}

	public boolean generate(World worldIn, EaglercraftRandom rand, BlockPos position) {
		this.world = worldIn;
		this.basePos = position;
		this.rand = new EaglercraftRandom(rand.nextLong(), !worldIn.getWorldInfo().isOldEaglercraftRandom());
		if (this.heightLimit == 0) {
			this.heightLimit = 5 + this.rand.nextInt(this.heightLimitLimit);
		}

		if (!this.validTreeLocation()) {
			return false;
		} else {
			this.generateLeafNodeList();
			this.generateLeaves();
			this.generateTrunk();
			this.generateLeafNodeBases();
			return true;
		}
	}

	/**+
	 * Returns a boolean indicating whether or not the current
	 * location for the tree, spanning basePos to to the height
	 * limit, is valid.
	 */
	private boolean validTreeLocation() {
		Block block = this.world.getBlockState(this.basePos.down()).getBlock();
		if (block != Blocks.dirt && block != Blocks.grass && block != Blocks.farmland) {
			return false;
		} else {
			int i = this.checkBlockLine(this.basePos, this.basePos.up(this.heightLimit - 1));
			if (i == -1) {
				return true;
			} else if (i < 6) {
				return false;
			} else {
				this.heightLimit = i;
				return true;
			}
		}
	}

	static class FoliageCoordinates extends BlockPos {
		private final int field_178000_b;

		public FoliageCoordinates(BlockPos parBlockPos, int parInt1) {
			super(parBlockPos.getX(), parBlockPos.getY(), parBlockPos.getZ());
			this.field_178000_b = parInt1;
		}

		public int func_177999_q() {
			return this.field_178000_b;
		}
	}
}