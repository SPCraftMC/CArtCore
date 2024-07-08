package moe.muska.ami.spcraft.cartcore;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CArtCoreLoader implements PluginLoader {

    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        MavenLibraryResolver resolver = new MavenLibraryResolver();

        List<String> dependencies = new ArrayList<>();
        dependencies.add("org.jetbrains.kotlin:kotlin-stdlib:1.9.24");
        dependencies.add("com.alibaba.fastjson2:fastjson2-kotlin:2.0.51");
        dependencies.add("com.zaxxer:HikariCP:5.1.0");

        List<List<String>> remotes = new ArrayList<>();
        remotes.add(List.of("huaweicloud", "default", "https://mirrors.huaweicloud.com/repository/maven/"));
        remotes.add(List.of("aliyun", "default", "https://maven.aliyun.com/nexus/content/groups/public/"));
        remotes.add(List.of("fastmirror", "default", "https://maven.fastmirror.net/repositories/minecraft/"));

        for (String dependency : dependencies) {
            resolver.addDependency(new Dependency(new DefaultArtifact(dependency), null));
        }

        for (List<String> remote : remotes) {
            resolver.addRepository(new RemoteRepository.Builder(remote.get(0), remote.get(1), remote.get(2)).build());
        }

        classpathBuilder.addLibrary(resolver);
    }
}
