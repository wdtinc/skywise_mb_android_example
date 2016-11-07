package com.wdtinc.mapbox_skywise_tiles_client;

import android.util.Log;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.Filter;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.NoSuchLayerException;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.sources.NoSuchSourceException;
import com.mapbox.mapboxsdk.style.sources.Source;
import com.mapbox.mapboxsdk.style.sources.TileSet;
import com.mapbox.mapboxsdk.style.sources.VectorSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * SkyWise Tiles 'Frame' object and its Mapbox SDK layers.
 */
public final class MapFrame {
    public static final float DEFAULT_OPACITY = 1f;

    /**
     * Metadata for a map frame.
     */
    public static final class Meta {
        private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);

        public final String uuid;
        public final Date validTime;

        public Meta(String uuid, String validTime) throws ParseException {
            this(uuid, FORMATTER.parse(validTime));
        }

        public Meta(String uuid, Date validTime) {
            this.uuid = uuid;
            this.validTime = validTime;
        }
    }


    public static MapFrame createMapFrame(MapboxMap mapboxMap, String host, String port, Meta fm) {
        final String frameUuid = fm.uuid;
        final String sourceId = "src-" + fm.validTime;
        final String layerId = "layer-" + fm.validTime;

        final TileSet tileSet = new TileSet(
                "2.1.0",
                "http://" + host + ":" + port + "/v2/frames/" + frameUuid + "/tile/{z}/{x}/{y}.mvt");

        final Source source = new VectorSource(sourceId, tileSet);
        mapboxMap.addSource(source);


        final List<String> layerIds = new ArrayList<>(ColorStep.DEFAULT_DBZ_COLOR_STEPS.size());

        for(ColorStep s : ColorStep.DEFAULT_DBZ_COLOR_STEPS) {
            String nextLayerId = layerId + s.max + "," + s.min;
            layerIds.add(nextLayerId);

            final FillLayer layer = new FillLayer(nextLayerId, sourceId);
            layer.setSourceLayer("reflectivity");
            layer.setFilter(
                    Filter.all(
                            Filter.gt("dbz", s.min),
                            Filter.lte("dbz", s.max)
                    )
            );
            layer.setProperties(
                    PropertyFactory.fillColor(s.rgbaColor),
                    PropertyFactory.fillOpacity(DEFAULT_OPACITY),
                    PropertyFactory.fillAntialias(true)
            );
            mapboxMap.addLayer(layer);
        }

        return new MapFrame(fm, sourceId, layerIds);
    }


    private Meta meta;
    private final String sourceId;
    private final List<String> layerIds;

    public MapFrame(Meta meta, String sourceId, List<String> layerIds) {
        this.meta = meta;
        this.sourceId = sourceId;
        this.layerIds = layerIds;
    }

    public void removeFrom(MapboxMap map) {
        try {
            for(String layerId : layerIds) {
                map.removeLayer(layerId);
            }
            map.removeSource(sourceId);
        } catch (NoSuchSourceException | NoSuchLayerException e) {
            Log.e(MapFrame.class.getCanonicalName(), e.getMessage(), e);
        }
    }

    public void hide(MapboxMap map) {
        for(String layerId : layerIds) {
            Layer layer = map.getLayer(layerId);
            if(layer != null) {
                if(Property.VISIBLE.equals(layer.getVisibility().getValue())) {
                    layer.setProperties(PropertyFactory.visibility(Property.NONE));
                }
            }
        }
    }

    public void show(MapboxMap map) {
        for(String layerId : layerIds) {
            Layer layer = map.getLayer(layerId);
            if(layer != null) {
                if(Property.NONE.equals(layer.getVisibility().getValue())) {
                    layer.setProperties(PropertyFactory.visibility(Property.VISIBLE));
                }
            }
        }
    }

    public void setOpacity(MapboxMap map, float opacity) {
        for(String layerId : layerIds) {
            Layer layer = map.getLayer(layerId);
            if(layer != null) {
                layer.setProperties(PropertyFactory.fillOpacity(opacity));
            }
        }
    }

    public Meta getMeta() {
        return meta;
    }
}
