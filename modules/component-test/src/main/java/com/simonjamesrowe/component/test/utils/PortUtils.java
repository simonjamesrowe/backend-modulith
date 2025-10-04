package com.simonjamesrowe.component.test.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;

@Slf4j
public final class PortUtils {

    private PortUtils() {
        // Utility class
    }

    private static final int MIN_PORT = 10000;
    private static final int MAX_PORT = 65535;
    private static final Random RANDOM = new Random();

    /**
     * Generates a random available port between MIN_PORT and MAX_PORT.
     *
     * @return a random available port number
     */
    public static int randomPort() {
        int port;
        do {
            port = MIN_PORT + RANDOM.nextInt(MAX_PORT - MIN_PORT);
        } while (!isPortAvailable(port));
        return port;
    }

    /**
     * Checks if a port is available for use.
     *
     * @param port the port to check
     * @return true if the port is available, false otherwise
     */
    public static boolean isPortAvailable(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            socket.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}