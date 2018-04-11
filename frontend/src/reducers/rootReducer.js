import {combineReducers} from "redux";
import controls from "../pages/graph/redux/controls";

export default function rootReducer(state = {}, action) {

    return combineReducers({
        controls
    })(state, action);

}
