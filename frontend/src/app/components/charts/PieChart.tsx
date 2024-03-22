"use client";

import {Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js"
import { Pie } from "react-chartjs-2";

const PieChart = ({data}: any) => {
    ChartJS.register(
        ArcElement,
        Tooltip,
        Legend
    );

    const options = {

    }

    return (
        <div className="size-96">
            <Pie data={data}  options={options} />
        </div>
    );
}   

export default PieChart