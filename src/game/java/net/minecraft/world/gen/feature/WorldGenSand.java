package net.minecraft.world.gen.feature;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
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
public class WorldGenSand extends WorldGenerator {
	private Block block;
	private int radius;

	public WorldGenSand(Block parBlock, int parInt1) {
		this.block = parBlock;
		this.radius = parInt1;
	}

	public boolean generate(World world, EaglercraftRandom random, BlockPos blockpos) {
		if (world.getBlockState(blockpos).getBlock().getMaterial() != Material.water) {
			return false;
		} else {
			int i = random.nextInt(this.radius - 2) + 2;
			byte b0 = 2;

			for (int j = blockpos.getX() - i; j <= blockpos.getX() + i; ++j) {
				for (int k = blockpos.getZ() - i; k <= blockpos.getZ() + i; ++k) {
					int l = j - blockpos.getX();
					int i1 = k - blockpos.getZ();
					if (l * l + i1 * i1 <= i * i) {
						for (int j1 = blockpos.getY() - b0; j1 <= blockpos.getY() + b0; ++j1) {
							BlockPos blockpos1 = new BlockPos(j, j1, k);
							Block blockx = world.getBlockState(blockpos1).getBlock();
							if (blockx == Blocks.dirt || blockx == Blocks.grass) {
								world.setBlockState(blockpos1, this.block.getDefaultState(), 2);
							}
						}
					}
				}
			}

			return true;
		}
	}
}