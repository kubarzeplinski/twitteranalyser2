const defaultState = {
    isInfoDialogOpen: false,
    userData: {},
    isUserDialogOpen: false
};

const prefix = "graph-page/";

const INFO_BUTTON_CLICKED = prefix + "info-button/clicked";
const INFO_DIALOG_CLOSED = prefix + "info-dialog/closed";
const USER_DATA_FETCHED = prefix + "user-data/fetched";
const USER_DIALOG_OPENED = prefix + "user-dialog/opened";
const USER_DIALOG_CLOSED = prefix + "user-dialog/closed";

export default function reducer(state = defaultState, action) {
    switch (action.type) {
        case INFO_BUTTON_CLICKED: {
            return {
                ...state,
                isInfoDialogOpen: true
            };
        }
        case INFO_DIALOG_CLOSED: {
            return {
                ...state,
                isInfoDialogOpen: false
            };
        }
        case USER_DATA_FETCHED: {
            return {
                ...state,
                userData: action.userData
            }
        }
        case USER_DIALOG_OPENED: {
            return {
                ...state,
                isUserDialogOpen: true
            }
        }
        case USER_DIALOG_CLOSED: {
            return {
                ...state,
                isUserDialogOpen: false
            }
        }
        default:
            return state;
    }
}

export function handleInfoButtonClick() {
    return {
        type: INFO_BUTTON_CLICKED,
    };
}

export function handleInfoDialogClose() {
    return {
        type: INFO_DIALOG_CLOSED,
    };
}

export function handleUserDataFetch(userData) {
    return {
        type: USER_DATA_FETCHED,
        userData
    }
}

export function handleUserDialogOpen() {
    return {
        type: USER_DIALOG_OPENED
    };
}

export function handleUserDialogClose() {
    return {
        type: USER_DIALOG_CLOSED
    };
}
