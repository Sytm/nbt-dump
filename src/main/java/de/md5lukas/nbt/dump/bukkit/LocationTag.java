package de.md5lukas.nbt.dump.bukkit;

import de.md5lukas.nbt.Tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class LocationTag extends Tag {

	private String world;
	private double x, y, z;

	public LocationTag(String name) {
		super(name);
	}

	@Override
	public void write(DataOutput dos) throws IOException {
		dos.writeUTF(world);
		dos.writeDouble(x);
		dos.writeDouble(y);
		dos.writeDouble(z);
	}

	@Override
	public void load(DataInput dis) throws IOException {
		world = dis.readUTF();
		x = dis.readDouble();
		y = dis.readDouble();
		z = dis.readDouble();
	}

	@Override
	public String toString() {
		return String.format("World: %s X: %f Y: %f Z: %f", world == null ? "null" : world, x, y, z);
	}

	@Override
	public byte getId() {
		return 100;
	}

	@Override
	public String getTagName() {
		return "TAG_Location";
	}

	@Override
	public Tag copy() {
		throw new UnsupportedOperationException();
	}
}