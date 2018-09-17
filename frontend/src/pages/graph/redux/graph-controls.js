import "whatwg-fetch";
import _ from "lodash";

const defaultState = {
    data: undefined,
    isDataLoading: false,
    keyword: "",
    keywords: []
};

const prefix = "graph-controls/";

const KEYWORD_CHANGED = prefix + "keyword/changed";
const KEYWORDS_FETCHED = prefix + "keywords/fetched";
const NEW_DATA_FETCHED = prefix + "new-data/fetched";
const SEARCH_BUTTON_CLICKED = prefix + "run-button/clicked";

export default function reducer(state = defaultState, action) {
    switch (action.type) {
        case KEYWORD_CHANGED: {
            return {
                ...state,
                keyword: action.keyword
            }
        }
        case KEYWORDS_FETCHED: {
            const keywords = action.keywords;
            return {
                ...state,
                keyword: !_.isNil(keywords[0]) ? keywords[0].name : "",
                keywords: keywords
            }
        }
        case SEARCH_BUTTON_CLICKED: {
            return {
                ...state,
                isKeywordInputBlocked: false,
                isSearchButtonBlocked: false,
                isDataLoading: true
            };
        }
        case NEW_DATA_FETCHED: {
            return {
                ...state,
                data: action.data,
                isDataLoading: false
            }
        }
        default:
            return state;
    }
}

export function handleKeywordChange(keyword) {
    return {
        type: KEYWORD_CHANGED,
        keyword
    };
}

export function handleKeywordsFetch(keywords) {
    return {
        type: KEYWORDS_FETCHED,
        keywords
    }
}

export function handleSearchButtonClick() {
    return {
        type: SEARCH_BUTTON_CLICKED
    };
}

export function handleNewDataFetch(data) {
    return {
        type: NEW_DATA_FETCHED,
        data
    };
}
