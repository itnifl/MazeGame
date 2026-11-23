<#-- The stylesheet of the screens between levels (win screen, shop), rendered by FxMenuTheme from the menu theme model
     (xmi/theme/menutheme.xmi). colour.X is a Swatch as rgba(), image.X the URL of an Art, slice.X its 9-slice inset,
     badge.X the colour of a shop tier. Edit the look in menutheme.mazegame, the structure here. -->
<#-- a 9-slice texture as the node's border image; width: how wide its edges are drawn (JavaFX counts it as inset) -->
<#macro plate art width=slice[art]>
    -fx-border-image-source: url("${image[art]}");
    -fx-border-image-slice: ${slice[art]?c} fill;
    -fx-border-image-width: ${width?c};
    -fx-border-image-repeat: stretch;
    -fx-background-color: transparent;
</#macro>
.menu-root {
    -fx-font-family: "${font}";
}

.menu-vignette {
    -fx-background-image: url("${image.VIGNETTE}");
    -fx-background-size: stretch;
    -fx-background-repeat: no-repeat;
}

.menu-panel {
<@plate "PANEL" 24/>
    -fx-padding: 8 14 8 14;
}

.menu-hazard {
    -fx-background-image: url("${image.HAZARD_STRIP}");
    -fx-background-repeat: repeat-x;
    -fx-background-position: left center;
    -fx-min-height: 12;
    -fx-pref-height: 12;
    -fx-max-height: 12;
}

.menu-banner {
<@plate "BANNER" 10/>
    -fx-padding: 0 26 0 26;
}

.menu-banner-text {
    -fx-text-fill: ${colour.TITLE};
    -fx-font-size: 26px;
    -fx-font-weight: bold;
    -fx-effect: dropshadow(gaussian, ${colour.SHADOW}, 3, 0.9, 0, 2);
}

.menu-banner-text.menu-title {
    -fx-font-size: 46px;
    -fx-padding: 2 18 2 18;
}

.menu-combo {
<@plate "CARD" 7/>
    -fx-font-size: 16px;
    -fx-cursor: hand;
}
.menu-combo:hover, .menu-combo:showing {
<@plate "CARD_SELECTED" 7/>
}
.menu-combo .list-cell {
    -fx-text-fill: ${colour.TEXT};
    -fx-font-weight: bold;
    -fx-background-color: transparent;
    -fx-padding: 4 10 4 10;
}
.menu-combo .arrow-button {
    -fx-background-color: transparent;
}
.menu-combo .arrow {
    -fx-background-color: ${colour.PRICE};
}
.combo-box-popup .list-view {
    -fx-background-color: rgba(16,20,16,0.97);
    -fx-border-color: ${colour.PRICE};
    -fx-border-width: 1;
}
.combo-box-popup .list-cell {
    -fx-background-color: transparent;
    -fx-text-fill: ${colour.TEXT};
    -fx-font-family: "${font}";
    -fx-font-size: 15px;
    -fx-padding: 5 10 5 10;
}
.combo-box-popup .list-cell:filled:hover, .combo-box-popup .list-cell:filled:selected {
    -fx-background-color: rgba(85,94,38,0.9);
    -fx-text-fill: ${colour.TITLE};
}

.menu-heading {
    -fx-text-fill: ${colour.TITLE};
    -fx-font-size: 15px;
    -fx-font-weight: bold;
}

.menu-text { -fx-text-fill: ${colour.TEXT}; -fx-font-size: 14px; }
.menu-muted { -fx-text-fill: ${colour.MUTED}; -fx-font-size: 12px; }
.menu-accent { -fx-text-fill: ${colour.ACCENT}; -fx-font-weight: bold; }
.menu-price { -fx-text-fill: ${colour.PRICE}; -fx-font-weight: bold; }
.menu-too-dear { -fx-text-fill: ${colour.TOO_DEAR}; -fx-font-weight: bold; }
.menu-good { -fx-text-fill: ${colour.GOOD}; -fx-font-weight: bold; }
.menu-bad { -fx-text-fill: ${colour.BAD}; -fx-font-weight: bold; }

.menu-credits {
    -fx-text-fill: ${colour.ACCENT};
    -fx-font-size: 18px;
    -fx-font-weight: bold;
    -fx-effect: dropshadow(gaussian, ${colour.SHADOW}, 3, 0.8, 0, 1);
}

.menu-score {
    -fx-text-fill: ${colour.PRICE};
    -fx-font-size: 40px;
    -fx-font-weight: bold;
    -fx-effect: dropshadow(gaussian, ${colour.SHADOW}, 4, 0.8, 0, 2);
}

.menu-card {
<@plate "CARD" 7/>
    -fx-padding: 0 4 0 0;
    -fx-cursor: hand;
}
.menu-card.selected {
<@plate "CARD_SELECTED" 7/>
}
.menu-card.locked {
<@plate "CARD_LOCKED" 7/>
}
.menu-card.locked.selected {
<@plate "CARD_SELECTED" 7/>
    -fx-opacity: 0.8;
}
.menu-card-title { -fx-text-fill: ${colour.TEXT}; -fx-font-size: 12px; -fx-font-weight: bold; }
.menu-card.locked .menu-card-title { -fx-text-fill: ${colour.MUTED}; }
.menu-card-line { -fx-font-size: 12px; }

.menu-slot {
<@plate "SLOT" 4/>
}

.menu-shelf-header {
    -fx-padding: 0 0 2 2;
}
.tier-cheap { -fx-text-fill: ${badge.CHEAP}; -fx-font-size: 13px; -fx-font-weight: bold; }
.tier-medium { -fx-text-fill: ${badge.MEDIUM}; -fx-font-size: 13px; -fx-font-weight: bold; }
.tier-expensive { -fx-text-fill: ${badge.EXPENSIVE}; -fx-font-size: 13px; -fx-font-weight: bold; }

.menu-briefing {
    -fx-text-fill: ${colour.TEXT};
    -fx-font-size: 13px;
    -fx-font-style: italic;
}

.menu-button {
    -fx-text-fill: ${colour.BUTTON_TEXT};
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-padding: 0 14 0 14;
    -fx-cursor: hand;
    -fx-background-insets: 0;
}
.menu-button .text {
    -fx-effect: dropshadow(gaussian, ${colour.SHADOW}, 2, 0.9, 0, 1);
}
.menu-button:hover {
    -fx-effect: dropshadow(gaussian, ${colour.PRICE}, 14, 0.3, 0, 0);
}
.menu-button:pressed {
    -fx-translate-y: 1;
}
.menu-button:disabled {
    -fx-opacity: 0.45;
}
<#list buttons as button>
.menu-button.${button.styleClass} {
<@plate button.art 9/>
    -fx-min-height: 36;
}
</#list>

.tooltip {
    -fx-background-color: #101410ee;
    -fx-text-fill: ${colour.TEXT};
    -fx-font-family: "${font}";
}
