package com.simonjamesrowe.component.test.containers;

import org.testcontainers.containers.GenericContainer;
import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;

@Slf4j
public final class ContainerConfigurationUtils {

    private ContainerConfigurationUtils() {
        // Utility class
    }

    private static final String TEST_CONTAINERS_DOCKER_GROUP_ADD = "TEST_CONTAINERS_DOCKER_GROUP_ADD";

    /**
     * Applies the TEST_CONTAINERS_DOCKER_GROUP_ADD environment variable as a --group-add parameter
     * to the container if the environment variable is set.
     *
     * @param container the container to configure
     * @param <T> the container type
     * @return the configured container
     */
    public static <T extends GenericContainer<?>> T applyGroupAddConfiguration(final T container) {
        final String groupAdd = System.getenv(TEST_CONTAINERS_DOCKER_GROUP_ADD);
        if (groupAdd != null && !groupAdd.trim().isEmpty()) {
            log.info("Applying --group-add {} to container", groupAdd);
            container.withCreateContainerCmdModifier(cmd -> {
                if (cmd.getHostConfig() == null) {
                    cmd.withHostConfig(new com.github.dockerjava.api.model.HostConfig());
                }
                cmd.getHostConfig().withGroupAdd(Arrays.asList(groupAdd));
            });
        }
        return container;
    }
}