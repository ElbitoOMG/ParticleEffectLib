# ParticleEffectLib
A library for Bukkit/Spigot servers to display all vanilla particle effects and some custom made bigger effects.

It's based on [this post](https://bukkit.org/threads/1-8-particleeffect-v1-7.154406/) and it's dependencies.

## Include in your project
First make sure you have this in your `build.gradle` file:

```gradle
repositories {
    jcenter()
}
```

### Maven
```xml
<dependency> 
  <groupId>de.lalo5</groupId> 
  <artifactId>particleeffectlib</artifactId> 
  <version>1.2</version> 
  <type>pom</type> 
</dependency>
```

### Gradle
```gradle
compile 'de.lalo5:particleeffectlib:1.2'
```

### Ivy
```xml
<dependency org='de.lalo5' name='particleeffectlib' rev='1.2'> 
  <artifact name='$AID' ext='pom'></artifact> 
</dependency>
```

### Direct
Download the latest [release](https://github.com/axelrindle/ParticleEffectLib/releases) and add it to your classpath. This varies from IDE to IDE.
