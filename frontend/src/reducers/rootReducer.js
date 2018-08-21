import {combineReducers} from "redux";
import statisticsControls from "../pages/statistics/redux/statistics-controls";
import statisticsPage from "../pages/statistics/redux/statistics-page";

export default function rootReducer(state = {}, action) {

    return combineReducers({
        statisticsPage,
        statisticsControls
    })(state, action);

}
