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
                <div>
                    <Link to="/statistics">
                        <Card elevation={Elevation.TWO}>
                            <h4>
                                Statistics&nbsp;
                                <Icon icon={IconNames.CHART} iconSize={Icon.SIZE_LARGE}/>
                            </h4>
                            <p>Analyse twitter live streaming and your historical data for chosen keyword.</p>
                            <ul>
                                <li>Latest 5 users</li>
                                <li>Number of users</li>
                                <li>Top 5 locations</li>
                                <li>Top 5 users by followers</li>
                            </ul>
                        </Card>
                    </Link>
                    <Link to="/graph">
                        <Card elevation={Elevation.TWO}>
                            <h4>
                                Graph&nbsp;
                                <Icon icon={IconNames.GRAPH} iconSize={Icon.SIZE_LARGE}/>
                            </h4>
                            <p>Analyse your historical twitter data from live streaming sessions for chosen keyword.</p>
                        </Card>
                    </Link>
                </div>
            </div>
        );
    }

}