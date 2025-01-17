package net.pl3x.map.api;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import net.pl3x.map.api.marker.Marker;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A simple {@link LayerProvider} implementation, backed by a Map.
 */
public final class SimpleLayerProvider implements LayerProvider {

    private final Supplier<String> labelSupplier;
    private final Map<Key, Marker> markers = new ConcurrentHashMap<>();
    private final boolean defaultHidden;
    private final boolean showControls;
    private final int layerPriority;
    private final int zIndex;

    private SimpleLayerProvider(final @NonNull Supplier<String> labelSupplier, final boolean defaultHidden, final boolean showControls, final int layerPriority, final int zIndex) {
        this.labelSupplier = labelSupplier;
        this.defaultHidden = defaultHidden;
        this.showControls = showControls;
        this.layerPriority = layerPriority;
        this.zIndex = zIndex;
    }

    /**
     * Get a new {@link SimpleLayerProvider.Builder} instance
     *
     * @param label label for the layer
     * @return new builder
     */
    public static SimpleLayerProvider.@NonNull Builder builder(final @NonNull String label) {
        return new Builder(() -> label);
    }

    /**
     * Get a new {@link SimpleLayerProvider.Builder} instance
     *
     * @param labelSupplier label supplier for the layer
     * @return new builder
     */
    public static SimpleLayerProvider.@NonNull Builder builder(final @NonNull Supplier<String> labelSupplier) {
        return new Builder(labelSupplier);
    }

    /**
     * Add a new marker to this layer
     *
     * @param key    key
     * @param marker marker
     */
    public void addMarker(final @NonNull Key key, final @NonNull Marker marker) {
        this.markers.put(key, marker);
    }

    /**
     * Remove an existing marker from this layer, returning either the removed marker, or {@code null} if
     * no marker was present for the provided key.
     *
     * @param key key
     * @return the existing marker or {@code null}
     */
    public @Nullable Marker removeMarker(final @NonNull Key key) {
        return this.markers.remove(key);
    }

    /**
     * Remove all registered markers
     */
    public void clearMarkers() {
        this.markers.clear();
    }

    /**
     * Get an unmodifiable view of the registered markers
     *
     * @return registered markers
     */
    public @NonNull Map<Key, Marker> registeredMarkers() {
        return Collections.unmodifiableMap(this.markers);
    }

    /**
     * Check whether a marker is registered for a key
     *
     * @param key key
     * @return whether a marker is registered
     */
    public boolean hasMarker(final @NonNull Key key) {
        return this.markers.containsKey(key);
    }

    @Override
    public @NonNull String getLabel() {
        return this.labelSupplier.get();
    }

    @Override
    public boolean showControls() {
        return this.showControls;
    }

    @Override
    public boolean defaultHidden() {
        return this.defaultHidden;
    }

    @Override
    public int layerPriority() {
        return this.layerPriority;
    }

    @Override
    public int zIndex() {
        return this.zIndex;
    }

    @Override
    public @NonNull Collection<Marker> getMarkers() {
        return this.markers.values();
    }

    /**
     * Builder for {@link SimpleLayerProvider}
     */
    public static final class Builder {

        private final Supplier<String> labelSupplier;
        private boolean defaultHidden = false;
        private boolean showControls = true;
        private int layerPriority = 99;
        private int zIndex = 99;

        private Builder(final @NonNull Supplier<String> labelSupplier) {
            this.labelSupplier = labelSupplier;
        }

        /**
         * Set whether this layer is hidden by default
         *
         * <p>Default: {@code false}</p>
         *
         * @param defaultHidden whether to be default hidden
         * @return this builder
         * @see LayerProvider#defaultHidden()
         */
        public @NonNull Builder defaultHidden(final boolean defaultHidden) {
            this.defaultHidden = defaultHidden;
            return this;
        }

        /**
         * Set whether to show controls for this layer
         *
         * <p>Default: {@code true}</p>
         *
         * @param showControls whether to show controls
         * @return this builder
         * @see LayerProvider#showControls()
         */
        public @NonNull Builder showControls(final boolean showControls) {
            this.showControls = showControls;
            return this;
        }

        /**
         * Set the priority for this layer
         *
         * <p>Default: {@code 99}</p>
         *
         * @param layerPriority layer priority
         * @return this builder
         * @see LayerProvider#layerPriority()
         */
        public @NonNull Builder layerPriority(final int layerPriority) {
            this.layerPriority = layerPriority;
            return this;
        }

        /**
         * Set the z-index for this layer
         *
         * <p>Default: {@code 99}</p>
         *
         * @param zIndex new z-index
         * @return this builder
         * @see LayerProvider#zIndex()
         */
        public @NonNull Builder zIndex(final int zIndex) {
            this.zIndex = zIndex;
            return this;
        }

        /**
         * Build a {@link SimpleLayerProvider} instance from the current state of this builder
         *
         * @return the built instance
         */
        public @NonNull SimpleLayerProvider build() {
            return new SimpleLayerProvider(this.labelSupplier, this.defaultHidden, this.showControls, this.layerPriority, this.zIndex);
        }

    }

}
