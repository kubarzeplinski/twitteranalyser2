const defaultState = {
    isInfoDialogOpen: false
};

const prefix = "graph-page/";

const INFO_BUTTON_CLICKED = prefix + "info-button/clicked";
const INFO_DIALOG_CLOSED = prefix + "info-dialog/closed";

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
