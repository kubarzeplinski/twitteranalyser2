import "whatwg-fetch";

const defaultState = {
    isKeywordInputBlocked: false,
    isSearchButtonBlocked: true,
    keyword: "",
    data: undefined,
    isDataLoading: false
};

const prefix = "graph-controls/";

const KEYWORD_ADDED = prefix + "keyword/added";
const KEYWORD_REMOVED = prefix + "keyword/removed";
const SEARCH_BUTTON_CLICKED = prefix + "run-button/clicked";
const NEW_DATA_FETCHED = prefix + "new-data";

export default function reducer(state = defaultState, action) {
    switch (action.type) {
        case KEYWORD_ADDED: {
            return {
                ...state,
                isKeywordInputBlocked: false,
                isSearchButtonBlocked: false,
                keyword: action.keyword
            };
        }
        case KEYWORD_REMOVED: {
            return {
                ...state,
                isKeywordInputBlocked: false,
                isSearchButtonBlocked: true,
                keyword: action.keyword
            };
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
    if (_.isEmpty(keyword)) {
        return {
            type: KEYWORD_REMOVED,
            keyword
        }
    }
    return {
        type: KEYWORD_ADDED,
        keyword
    };
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
