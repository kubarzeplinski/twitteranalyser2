import Controls from "./components/controls/ControlsContainer";
import Graph from "./components/graph/Graph";
import React from "react";


export default class GraphPage extends React.Component {

    render() {
        return (
            <div className="panel-content">
                <Controls/>
                <Graph/>
            </div>
        );
    }

}