const defaultState = {
    isKeywordInputBlocked: false,
    isRunButtonBlocked: true,
    isStopButtonBlocked: true,
    isWebSocketInitialized: false,
    keyword: "",
};

const prefix = "controls/";

const KEYWORD_ADDED = prefix + "keyword/added";
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

export function handleKeywordAdd(keyword) {
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
        stompClient.subscribe('/dashboard/graphData', function (data) {
            //TODO send data to graph
        });
    });
    return {
        type: WEB_SOCKET_INITIALIZED,
    }
}

function sendKeyword(keyword) {
    stompClient.send("/app/dashboard/graphData", {}, JSON.stringify({"name": keyword}));
}
