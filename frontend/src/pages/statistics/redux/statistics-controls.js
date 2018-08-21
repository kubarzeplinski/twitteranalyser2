import "whatwg-fetch";

const defaultState = {
    isKeywordInputBlocked: false,
    isRunButtonBlocked: true,
    isStopButtonBlocked: true,
    isWebSocketInitialized: false,
    keyword: "",
};

const prefix = "statistics-controls/";

const KEYWORD_ADDED = prefix + "keyword/added";
const KEYWORD_REMOVED = prefix + "keyword/removed";
const RUN_BUTTON_CLICKED = prefix + "run-button/clicked";
const STOP_BUTTON_CLICKED = prefix + "stop-button/clicked";
const WEB_SOCKET_INITIALIZED = prefix + "web-socket/initialized";

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
                isRunButtonBlocked: true,
                isStopButtonBlocked: false,
            };
        }
        case STOP_BUTTON_CLICKED: {
            stop();
            return {
                ...state,
                isKeywordInputBlocked: false,
                isRunButtonBlocked: false,
                isStopButtonBlocked: true,
            };
        }
        case WEB_SOCKET_INITIALIZED: {
            return {
                ...state,
                isWebSocketInitialized: true,
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

let stompClient = null;

export function initWebSocket() {
    const socket = new SockJS('http://localhost:8080/twitter-analyser');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/statistics/statisticsData', function (data) {
            //TODO send data to graph
        });
    });
    return {
        type: WEB_SOCKET_INITIALIZED,
    }
}

function sendKeyword(keyword) {
    fetch('http://localhost:8080/statistics/start', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            nodeId: 1,
            name: keyword,
        })
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
