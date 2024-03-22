"use client";

import { Friday, Pizza, PizzaLastFridays } from "../../../types";
import AreaChart from "./charts/AreaChart";

const DashboardAreaChart = ({ fridays }: PizzaLastFridays) => {

    let meatArray: number[] = [];
    let veggieArray: number[] = [];
    let veganArray: number[] = [];

    if (fridays === undefined) return;

    fridays.map((friday: Friday) => {
        friday.pizzas.map((pizza: Pizza) => {
            if (pizza.type === "MEAT") {
                return meatArray.push(pizza.slices);
            }
            if (pizza.type === "VEGGIE") {
                return veggieArray.push(pizza.slices);
            }
            if (pizza.type === "VEGAN") {
                return veganArray.push(pizza.slices);
            }
        })
        return;
    })

    const data = {
        labels: fridays.map((friday: Friday) => {
            return new Date(friday.date).toLocaleDateString();
        }),
        datasets: [
            {
                label: "MEAT",
                data: meatArray.length === 0 ? [0,0,0,0,0,0,0,0] : meatArray,
                borderColor: 'rgb(227, 84, 141)',
                backgroundColor: ["rgba(227, 84, 141, 0.5)"]
            },
            {
                label: "VEGGIE",
                data: veggieArray.length === 0 ? [0,0,0,0,0,0,0,0] : veggieArray,
                borderColor: 'rgb(153, 84, 227)',
                backgroundColor: ["rgba(153, 84, 227, 0.5)"]
            },
            {
                label: "VEGAN",
                data: veganArray.length === 0 ? [0,0,0,0,0,0,0,0] : veganArray,
                borderColor: 'rgb(13, 136, 212)',
                backgroundColor: ["rgba(13, 136, 212, 0.5)"]
            }
        ]
    };

    //TODO

    return (
        <div className="mt-20 ml-6 h-[40rem] w-full bg-white rounded-lg shadow dark:border dark:bg-gray-800 dark:border-gray-700">
            <div className="ml-10 mt-12">
                <AreaChart data={data} />
            </div>
        </div>
    );
}

export default DashboardAreaChart