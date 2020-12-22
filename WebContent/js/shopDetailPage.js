
(function() {

    const mySlider = $("#slide-bar").wRunner({
        // step size
        step: 100,

        // or 'range'
        type: "range",

        // min/max values
        limits: {
            minLimit: 0, 
            maxLimit: 128000
        },

        // default value
        singleValue: 1000,
        rangeValue: { 
            minValue: 20, 
            maxValue: 126000
        },

        // root element
        sliderRoots: document.querySelector(".header-filter-box-footer-right"),

        // the number of divisions
        divisionsCount: 10,

        // shows labels
        valueNoteDisplay: true,

        // theme name
        theme: "default",

        // or 'vertical'
        direction: 'horizontal'

    });        

})();

/**
 * .contents-wrapper > .card-container > .shop-detail 
 */

(function() {

    /**
     * @class Box
     */
    class Box {

        constructor(clazz) {

            /**
             * @type {HTMLDivElement}
             */
            this._o = document.querySelector(clazz);
            
            /**
             * @type {DOMRect}
             */
            const rect = this._o.getBoundingClientRect();
            
            this._x = rect.x;
            this._y = rect.y;
            this._width = rect.width;
            this._height = rect.height;

        }

        get name() {
            return this._o.className;
        }

        get x() {
            return this._x;
        }

        get y() {
            return this._y;
        }

        get width() {
            return this._width;
        }

        get height() {
            return this._height;
        }

        /**
         * @return {HTMLDivElement}
         */
        get box() {
            return this._o;
        }

    }

    class Segment extends Box {
        update() {

        }
    }
    
    class SegmentLeft extends Segment {
    }

    class SegmentMiddle extends Segment {
    }

    class SegmentRight extends Segment {
    }

    class Container extends Segment {
    }

    class SegmentController {
        constructor() {
            this.addModels();
            this.update();
        }

        addModels() {

            /**
             * @type {Segment[]}
             */
            this._segments = [];
            this._segments.push( new SegmentLeft(".detail-trailer-list") );
            this._segments.push( new SegmentMiddle(".centered") );
            this._segments.push( new SegmentRight(".list-container") );

            this._container = new Container(`.shop-detail`);
        }

        update() {
            this._container.width - this._segments[1].width / 2;
        }
    }

})();