package com.rift.dipforge.groovy.lib;

import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.LinkOption.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * Example to watch a directory (or tree) for changes to files.
 */

public class WatchDir {

    private Logger log = Logger.getLogger(WatchDir.class);

	private final WatchService watcher;
	private final Map<WatchKey,Path> keys;
	private final boolean recursive;
	private boolean trace = false;

	@SuppressWarnings("unchecked")
	static <T> WatchEvent<T> cast(WatchEvent<?> event) {
		return (WatchEvent<T>)event;
	}

	/**
	 * Register the given directory with the WatchService
	 */
	private void register(Path dir) throws IOException {
        if (!dir.toFile().isDirectory()) {
            return;
        }
		WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		if (trace) {
			Path prev = keys.get(key);
			if (prev == null) {
				log.info(String.format("register: %s\n", dir));
			} else {
				if (!dir.equals(prev)) {
					log.info(String.format("update: %s -> %s\n", prev, dir));
				}
			}
		}
		keys.put(key, dir);
	}

	/**
	 * Register the given directory, and all its sub-directories, with the
	 * WatchService.
	 */
	private void registerAll(final Path start) throws IOException {
        if (!start.toFile().isDirectory()) {
            return;
        }
		// register directory and sub-directories
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
			throws IOException
		{
			register(dir);
			return FileVisitResult.CONTINUE;
		}
		});
	}

	/**
	 * Creates a WatchService and registers the given directory
	 */
	public WatchDir(Path dir, boolean recursive) throws IOException {
		this.watcher = FileSystems.getDefault().newWatchService();
		this.keys = new HashMap<WatchKey,Path>();
		this.recursive = recursive;

		if (recursive) {
			log.info(String.format("Scanning %s ...\n", dir));
			registerAll(dir);
			log.info("Done.");
		} else {
			register(dir);
		}

		// enable trace after initial registration
		this.trace = true;
	}

	/**
	 * check for changes
	 */
	public boolean changed() {

		// wait for key to be signalled
		WatchKey key = watcher.poll();
		if (key == null) {
			return false;
		} else {
			key.reset();
			return true;
		}
	}

}
