package net.shadowmage.ancientwarfare.structure.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.shadowmage.ancientwarfare.core.container.ContainerBase;
import net.shadowmage.ancientwarfare.core.network.NetworkHandler;
import net.shadowmage.ancientwarfare.core.network.PacketGui;
import net.shadowmage.ancientwarfare.structure.tile.SpawnerSettings;

public class ContainerSpawnerAdvanced extends ContainerBase
{

public SpawnerSettings settings;

public ContainerSpawnerAdvanced(EntityPlayer player, int x, int y, int z)
  {
  super(player, x, y, z);
  settings = new SpawnerSettings();
  ItemStack item = player.inventory.getCurrentItem();
  if(item==null || !item.hasTagCompound() || !item.getTagCompound().hasKey("spawnerSettings")){throw new IllegalArgumentException("stack cannot be null, and must have tag compounds!!");}
  settings.readFromNBT(item.getTagCompound().getCompoundTag("spawnerSettings"));
  }

public void sendSettingsToServer()
  {
  NBTTagCompound tag = new NBTTagCompound();
  settings.writeToNBT(tag);
  
  PacketGui pkt = new PacketGui();
  pkt.packetData.setTag("spawnerSettings", tag);
  NetworkHandler.sendToServer(pkt);
  }

@Override
public void handlePacketData(NBTTagCompound tag)
  {
  if(tag.hasKey("spawnerSettings"))
    {
    ItemStack item = player.inventory.getCurrentItem();
    item.setTagInfo("spawnerSettings", tag.getCompoundTag("spawnerSettings"));
    }
  }

}
