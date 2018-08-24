import {combineReducers} from "redux";
import statisticsControls from "../pages/statistics/redux/statistics-controls";
import statisticsPage from "../pages/statistics/redux/statistics-page";
import graphControls from "../pages/graph/redux/graph-controls";

export default function rootReducer(state = {}, action) {

    return combineReducers({
        statisticsPage,
        statisticsControls,
        graphControls
    })(state, action);

}
