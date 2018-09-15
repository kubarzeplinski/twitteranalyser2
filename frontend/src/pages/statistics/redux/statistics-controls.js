const defaultState = {
    isKeywordInputBlocked: false,
    isProcessingOn: false,
    isRunButtonBlocked: true,
    isStopButtonBlocked: true,
    keyword: ""
};

const prefix = "statistics-controls/";

const KEYWORD_ADDED = prefix + "keyword/added";
const KEYWORD_REMOVED = prefix + "keyword/removed";
const RUN_BUTTON_CLICKED = prefix + "run-button/clicked";
const STOP_BUTTON_CLICKED = prefix + "stop-button/clicked";

export default function reducer(state = defaultState, action) {
    switch (action.type) {
        case KEYWORD_ADDED: {
            return {
                ...state,
                isKeywordInputBlocked: false,
                isRunButtonBlocked: false,
                isStopButtonBlocked: true,
                keyword: action.keyword,
            };
        }
        case KEYWORD_REMOVED: {
            return {
                ...state,
                isKeywordInputBlocked: false,
                isRunButtonBlocked: true,
                isStopButtonBlocked: true,
                keyword: action.keyword,
            };
        }
        case RUN_BUTTON_CLICKED: {
            sendKeyword(state.keyword);
            return {
                ...state,
                isKeywordInputBlocked: true,
                isProcessingOn: true,
                isRunButtonBlocked: true,
                isStopButtonBlocked: false
            };
        }
        case STOP_BUTTON_CLICKED: {
            stop();
            return {
                ...state,
                isKeywordInputBlocked: false,
                isProcessingOn: false,
                isRunButtonBlocked: false,
                isStopButtonBlocked: true
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
        keyword,
    };
}

export function handleRunButtonClick() {
    return {
        type: RUN_BUTTON_CLICKED,
    };
}

export function handleStopButtonClick() {
    return {
        type: STOP_BUTTON_CLICKED,
    };
}

function sendKeyword(keyword) {
    fetch('http://localhost:8080/statistics/start', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({keyword})
    });
}

function stop() {
    fetch('http://localhost:8080/statistics/stop', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        }
    });
}
