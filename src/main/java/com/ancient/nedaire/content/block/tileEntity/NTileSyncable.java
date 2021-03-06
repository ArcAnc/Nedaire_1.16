package com.ancient.nedaire.content.block.tileEntity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;

public class NTileSyncable extends NTile 
{

	public NTileSyncable(TileEntityType<?> type) 
	{
		super(type);
	}
	
	public void sendUpdateToClient(boolean requiredUpdate)
	{
		if (requiredUpdate)
		{
			markDirty();
			
			BlockState state = getWorld().getBlockState(getPos());
			getWorld().notifyBlockUpdate(getPos(), state, state, 2);
			
		}
	}
	
	protected boolean canTick()
	{
		return getWorld().getGameTime() % workSpeed == 0 && !getWorld().isRemote();
	}
	
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() 
	{
		return new SUpdateTileEntityPacket(getPos(), 1, write(new CompoundNBT()));
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) 
	{
		// FIXME: ��������� ���� �����, ����� ����� ������ ����
		read(getBlockState(), pkt.getNbtCompound());
	}
	
	@Override
	public CompoundNBT getUpdateTag() 
	{
		return write(new CompoundNBT());
	}
	
	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) 
	{
		read(state, tag);
	}
	
	public void handleClientPacket(CompoundNBT tag)
	{
		
	}
}
