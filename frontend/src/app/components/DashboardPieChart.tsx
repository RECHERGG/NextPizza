"use client";

import PieChart from "./charts/PieChart";
import { DashboardCharts } from "../../../types";

const DashboardPieChart = ({meat, veggie, vegan}: DashboardCharts) => {

    const data = {
        labels: ["MEAT", "VEGGIE", "VEGAN"],
        datasets: [
            {
                data: [meat, veggie, vegan],
                borderColor: ["rgb(227, 84, 141)", "rgb(153, 84, 227)", "rgb(13, 136, 212)"],
                backgroundColor: ["rgba(227, 84, 141, 0.7)", "rgba(153, 84, 227, 0.7)", "rgba(13, 136, 212, 0.7)"]
            }
        ]
    };

    return (
        <div className="mt-44 ml-6 h-[27rem] w-[24rem] bg-white rounded-lg shadow dark:border dark:bg-gray-800 dark:border-gray-700">
            <div className="mt-5">
                <PieChart data={data}/>
            </div>
        </div>
    );
}

export default DashboardPieChart