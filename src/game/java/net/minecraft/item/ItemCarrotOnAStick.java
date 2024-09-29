package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
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
public class ItemCarrotOnAStick extends Item {
	public ItemCarrotOnAStick() {
		this.setCreativeTab(CreativeTabs.tabTransport);
		this.setMaxStackSize(1);
		this.setMaxDamage(25);
	}

	/**+
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	public boolean isFull3D() {
		return true;
	}

	/**+
	 * Returns true if this item should be rotated by 180 degrees
	 * around the Y axis when being held in an entities hands.
	 */
	public boolean shouldRotateAroundWhenRendering() {
		return true;
	}

	/**+
	 * Called whenever this item is equipped and the right mouse
	 * button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack itemstack, World var2, EntityPlayer entityplayer) {
		if (entityplayer.isRiding() && entityplayer.ridingEntity instanceof EntityPig) {
			EntityPig entitypig = (EntityPig) entityplayer.ridingEntity;
			if (entitypig.getAIControlledByPlayer().isControlledByPlayer()
					&& itemstack.getMaxDamage() - itemstack.getMetadata() >= 7) {
				entitypig.getAIControlledByPlayer().boostSpeed();
				itemstack.damageItem(7, entityplayer);
				if (itemstack.stackSize == 0) {
					ItemStack itemstack1 = new ItemStack(Items.fishing_rod);
					itemstack1.setTagCompound(itemstack.getTagCompound());
					return itemstack1;
				}
			}
		}

		entityplayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
		return itemstack;
	}
}