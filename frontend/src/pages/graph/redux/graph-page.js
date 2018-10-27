const defaultState = {
    isInfoDialogOpen: false,
    isRelationDialogOpen: false,
    isUserDialogOpen: false,
    relationData: [],
    userData: {}
};

const prefix = "graph-page/";

const INFO_BUTTON_CLICKED = prefix + "info-button/clicked";
const INFO_DIALOG_CLOSED = prefix + "info-dialog/closed";
const RELATION_DATA_FETCHED = prefix + "link-data/fetched";
const USER_DATA_FETCHED = prefix + "user-data/fetched";
const RELATION_DIALOG_CLOSED = prefix + "relation-dialog/closed";
const RELATION_DIALOG_OPENED = prefix + "relation-dialog/opened";
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
        case RELATION_DATA_FETCHED: {
            return{
                ...state,
                relationData: action.relationData
            }
        }
        case USER_DATA_FETCHED: {
            return {
                ...state,
                userData: action.userData
            }
        }
        case RELATION_DIALOG_OPENED: {
            return {
                ...state,
                isRelationDialogOpen: true
            }
        }
        case RELATION_DIALOG_CLOSED: {
            return {
                ...state,
                isRelationDialogOpen: false
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

export function handleRelationDataFetch(relationData) {
    return {
        type: RELATION_DATA_FETCHED,
        relationData
    }
}

export function handleUserDataFetch(userData) {
    return {
        type: USER_DATA_FETCHED,
        userData
    }
}

export function handleRelationDialogOpen() {
    return {
        type: RELATION_DIALOG_OPENED
    }
}

export function handleRelationDialogClose() {
    return {
        type: RELATION_DIALOG_CLOSED
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
