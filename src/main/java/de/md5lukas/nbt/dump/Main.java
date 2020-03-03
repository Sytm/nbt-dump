package de.md5lukas.nbt.dump;

import de.md5lukas.nbt.NbtIo;
import de.md5lukas.nbt.Tags;
import de.md5lukas.nbt.dump.bukkit.LocationTag;
import de.md5lukas.nbt.exceptions.InvalidTagException;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		Options options = new Options();
		options.addRequiredOption("i", "input", true, "File to dump");
		options.addOption("e", "extended", false, "Use extended set of tags");
		options.addOption("b", "bukkit", false, "Use custom set of bukkit related tags");
		options.addOption("c", "compressed", false, "If set to true the parser will expect a compressed nbt file");
		CommandLineParser parser = new DefaultParser();
		CommandLine line;
		try {
			line = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			new HelpFormatter().printHelp("nbt-dump.jar", options, true);
			return;
		}
		if (line.hasOption("extended")) {
			Tags.registerExtendedTags();
		}
		if (line.hasOption("bukkit")) {
			Tags.registerTag(LocationTag::new);
		}

		try {
			File file = new File(line.getOptionValue("input"));
			if (options.hasOption("compressed")) {
				NbtIo.readCompressed(file).print(System.out);
			} else {
				NbtIo.read(file).print(System.out);
			}
		} catch (IOException e) {
			System.err.println("An error occurred while trying to read the nbt file, perhaps the file doesn't exist or is compressed/uncompressed");
		} catch (InvalidTagException e) {
			System.err.println("The parser found unregistered tags in the nbt file, perhaps enable more with -e and/or -b");
		}
	}
}
