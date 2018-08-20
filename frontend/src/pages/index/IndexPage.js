import React from "react";
import {Card, Elevation, Icon} from "@blueprintjs/core";
import {IconNames} from "@blueprintjs/icons";
import {Link} from "react-router-dom";

export default class IndexPage extends React.Component {

    render() {
        return (
            <div className="panel-content">
                <Card elevation={Elevation.TWO}>
                    <h4>Welcome in Twitter Analyser application!</h4>
                    <p>Enjoy twitter data analysis.</p>
                </Card>
                <h4>Features</h4>
                <Link to="/statistics">
                    <Card elevation={Elevation.TWO}>
                        <h4>
                            Statistics&nbsp;
                            <Icon iconName={IconNames.CHART} iconSize={Icon.SIZE_LARGE}/>
                        </h4>
                        <p>Analyse twitter live streaming data for keyword.</p>
                    </Card>
                </Link>
                <Link to="/graph">
                    <Card elevation={Elevation.TWO}>
                        <h4>
                            Graph&nbsp;
                            <Icon iconName={IconNames.GRAPH} iconSize={Icon.SIZE_LARGE}/>
                        </h4>
                        <p>Analyse historical twitter data from live streaming sessions.</p>
                    </Card>
                </Link>
            </div>
        );
    }

}