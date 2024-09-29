package net.minecraft.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import net.lax1dude.eaglercraft.v1_8.EagRuntime;
import net.lax1dude.eaglercraft.v1_8.internal.FileChooserResult;
import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.lax1dude.eaglercraft.v1_8.minecraft.EaglerFolderResourcePack;
import net.lax1dude.eaglercraft.v1_8.minecraft.GuiScreenGenericErrorMessage;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.client.resources.ResourcePackListEntryDefault;
import net.minecraft.client.resources.ResourcePackListEntryFound;
import net.minecraft.client.resources.ResourcePackRepository;

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
public class GuiScreenResourcePacks extends GuiScreen {
	private static final Logger logger = LogManager.getLogger();
	private final GuiScreen parentScreen;
	private List<ResourcePackListEntry> availableResourcePacks;
	private List<ResourcePackListEntry> selectedResourcePacks;
	private GuiResourcePackAvailable availableResourcePacksList;
	private GuiResourcePackSelected selectedResourcePacksList;
	private boolean changed = false;

	public GuiScreenResourcePacks(GuiScreen parentScreenIn) {
		this.parentScreen = parentScreenIn;
	}

	/**+
	 * Adds the buttons (and other controls) to the screen in
	 * question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	public void initGui() {
		this.buttonList.add(new GuiOptionButton(2, this.width / 2 - 154, this.height - 48,
				I18n.format("resourcePack.openFolder", new Object[0])));
		this.buttonList.add(
				new GuiOptionButton(1, this.width / 2 + 4, this.height - 48, I18n.format("gui.done", new Object[0])));
		if (!this.changed) {
			this.availableResourcePacks = Lists.newArrayList();
			this.selectedResourcePacks = Lists.newArrayList();
			ResourcePackRepository resourcepackrepository = this.mc.getResourcePackRepository();
			resourcepackrepository.updateRepositoryEntriesAll();
			List arraylist = Lists.newArrayList(resourcepackrepository.getRepositoryEntriesAll());
			arraylist.removeAll(resourcepackrepository.getRepositoryEntries());

			for (int i = 0, l = arraylist.size(); i < l; ++i) {
				this.availableResourcePacks
						.add(new ResourcePackListEntryFound(this, (ResourcePackRepository.Entry) arraylist.get(i)));
			}

			arraylist = Lists.reverse(resourcepackrepository.getRepositoryEntries());
			for (int i = 0, l = arraylist.size(); i < l; ++i) {
				this.selectedResourcePacks
						.add(new ResourcePackListEntryFound(this, (ResourcePackRepository.Entry) arraylist.get(i)));
			}

			this.selectedResourcePacks.add(new ResourcePackListEntryDefault(this));
		}

		this.availableResourcePacksList = new GuiResourcePackAvailable(this.mc, 200, this.height,
				this.availableResourcePacks);
		this.availableResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 - 4 - 200);
		this.availableResourcePacksList.registerScrollButtons(7, 8);
		this.selectedResourcePacksList = new GuiResourcePackSelected(this.mc, 200, this.height,
				this.selectedResourcePacks);
		this.selectedResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 + 4);
		this.selectedResourcePacksList.registerScrollButtons(7, 8);
	}

	/**+
	 * Handles mouse input.
	 */
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		this.selectedResourcePacksList.handleMouseInput();
		this.availableResourcePacksList.handleMouseInput();
	}

	public void handleTouchInput() throws IOException {
		super.handleTouchInput();
		this.selectedResourcePacksList.handleTouchInput();
		this.availableResourcePacksList.handleTouchInput();
	}

	public boolean hasResourcePackEntry(ResourcePackListEntry parResourcePackListEntry) {
		return this.selectedResourcePacks.contains(parResourcePackListEntry);
	}

	/**+
	 * Returns the list containing the resource pack entry, returns
	 * the selected list if it is selected, otherwise returns the
	 * available list
	 */
	public List<ResourcePackListEntry> getListContaining(ResourcePackListEntry parResourcePackListEntry) {
		return this.hasResourcePackEntry(parResourcePackListEntry) ? this.selectedResourcePacks
				: this.availableResourcePacks;
	}

	/**+
	 * Returns a list containing the available resource packs
	 */
	public List<ResourcePackListEntry> getAvailableResourcePacks() {
		return this.availableResourcePacks;
	}

	/**+
	 * Returns a list containing the selected resource packs
	 */
	public List<ResourcePackListEntry> getSelectedResourcePacks() {
		return this.selectedResourcePacks;
	}

	/**+
	 * Called by the controls from the buttonList when activated.
	 * (Mouse pressed for buttons)
	 */
	protected void actionPerformed(GuiButton parGuiButton) {
		if (parGuiButton.enabled) {
			if (parGuiButton.id == 2) {
				EagRuntime.displayFileChooser("application/zip", "zip");
			} else if (parGuiButton.id == 1) {
				if (this.changed) {
					ArrayList<ResourcePackRepository.Entry> arraylist = Lists.newArrayList();

					for (int i = 0, l = this.selectedResourcePacks.size(); i < l; ++i) {
						ResourcePackListEntry resourcepacklistentry = this.selectedResourcePacks.get(i);
						if (resourcepacklistentry instanceof ResourcePackListEntryFound) {
							arraylist.add(((ResourcePackListEntryFound) resourcepacklistentry).func_148318_i());
						}
					}

					Collections.reverse(arraylist);
					this.mc.getResourcePackRepository().setRepositories(arraylist);
					this.mc.gameSettings.resourcePacks.clear();
					this.mc.gameSettings.field_183018_l.clear();

					for (int i = 0, l = arraylist.size(); i < l; ++i) {
						ResourcePackRepository.Entry resourcepackrepository$entry = arraylist.get(i);
						this.mc.gameSettings.resourcePacks.add(resourcepackrepository$entry.getResourcePackName());
						if (resourcepackrepository$entry.func_183027_f() != 1) {
							this.mc.gameSettings.field_183018_l.add(resourcepackrepository$entry.getResourcePackName());
						}
					}

					this.mc.loadingScreen.eaglerShow(I18n.format("resourcePack.load.refreshing"),
							I18n.format("resourcePack.load.pleaseWait"));
					this.mc.gameSettings.saveOptions();
					this.mc.refreshResources();
				}
				this.mc.displayGuiScreen(this.parentScreen);
			}

		}
	}

	public void updateScreen() {
		FileChooserResult packFile = null;
		if (EagRuntime.fileChooserHasResult()) {
			packFile = EagRuntime.getFileChooserResult();
		}
		if (packFile == null) {
			return;
		}
		mc.loadingScreen.eaglerShow(I18n.format("resourcePack.load.loading"), packFile.fileName);
		try {
			EaglerFolderResourcePack.importResourcePack(packFile.fileName, EaglerFolderResourcePack.RESOURCE_PACKS,
					packFile.fileData);
		} catch (IOException e) {
			logger.error("Could not load resource pack: {}", packFile.fileName);
			logger.error(e);
			mc.displayGuiScreen(new GuiScreenGenericErrorMessage("resourcePack.importFailed.1",
					"resourcePack.importFailed.2", parentScreen));
			return;
		}

		ArrayList<ResourcePackRepository.Entry> arraylist = Lists.newArrayList();

		for (int i = 0, l = this.selectedResourcePacks.size(); i < l; ++i) {
			ResourcePackListEntry resourcepacklistentry = this.selectedResourcePacks.get(i);
			if (resourcepacklistentry instanceof ResourcePackListEntryFound) {
				arraylist.add(((ResourcePackListEntryFound) resourcepacklistentry).func_148318_i());
			}
		}

		Collections.reverse(arraylist);
		this.mc.getResourcePackRepository().setRepositories(arraylist);
		this.mc.gameSettings.resourcePacks.clear();
		this.mc.gameSettings.field_183018_l.clear();

		for (int i = 0, l = arraylist.size(); i < l; ++i) {
			ResourcePackRepository.Entry resourcepackrepository$entry = arraylist.get(i);
			this.mc.gameSettings.resourcePacks.add(resourcepackrepository$entry.getResourcePackName());
			if (resourcepackrepository$entry.func_183027_f() != 1) {
				this.mc.gameSettings.field_183018_l.add(resourcepackrepository$entry.getResourcePackName());
			}
		}

		this.mc.gameSettings.saveOptions();

		boolean wasChanged = this.changed;
		this.changed = false;
		this.initGui();
		this.changed = wasChanged;
	}

	/**+
	 * Called when the mouse is clicked. Args : mouseX, mouseY,
	 * clickedButton
	 */
	protected void mouseClicked(int parInt1, int parInt2, int parInt3) {
		super.mouseClicked(parInt1, parInt2, parInt3);
		this.availableResourcePacksList.mouseClicked(parInt1, parInt2, parInt3);
		this.selectedResourcePacksList.mouseClicked(parInt1, parInt2, parInt3);
	}

	/**+
	 * Called when a mouse button is released. Args : mouseX,
	 * mouseY, releaseButton
	 */
	protected void mouseReleased(int i, int j, int k) {
		super.mouseReleased(i, j, k);
	}

	/**+
	 * Draws the screen and all the components in it. Args : mouseX,
	 * mouseY, renderPartialTicks
	 */
	public void drawScreen(int i, int j, float f) {
		this.drawBackground(0);
		this.availableResourcePacksList.drawScreen(i, j, f);
		this.selectedResourcePacksList.drawScreen(i, j, f);
		this.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.title", new Object[0]), this.width / 2,
				16, 16777215);
		this.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.folderInfo", new Object[0]),
				this.width / 2 - 77, this.height - 26, 8421504);
		super.drawScreen(i, j, f);
	}

	/**+
	 * Marks the selected resource packs list as changed to trigger
	 * a resource reload when the screen is closed
	 */
	public void markChanged() {
		this.changed = true;
	}
}