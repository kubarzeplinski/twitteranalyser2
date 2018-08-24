import "whatwg-fetch";

const defaultState = {
    isKeywordInputBlocked: false,
    isSearchButtonBlocked: true,
    keyword: "",
};

const prefix = "graph-controls/";

const KEYWORD_ADDED = prefix + "keyword/added";
const KEYWORD_REMOVED = prefix + "keyword/removed";
const SEARCH_BUTTON_CLICKED = prefix + "run-button/clicked";

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
            sendKeyword(state.keyword);
            return {
                ...state,
                isKeywordInputBlocked: false,
                isSearchButtonBlocked: false
            };
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

function sendKeyword(keyword) {
    fetch('http://localhost:8080/graph/' + keyword, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then((resp) => resp.json())
        .then((data) => console.log(data));
}
