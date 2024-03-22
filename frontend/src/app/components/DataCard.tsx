"use client";

import { DataCardProps } from "../../../types";


const DataCard = ({ title, value, containerStyles }: DataCardProps) => {
    return (
        <div className="h-36 bg-white rounded-lg shadow dark:border dark:bg-gray-800 dark:border-gray-700">
            <h2 className="mt-2 text-center font-semibold text-slate-300">{title}</h2>
            {title !== "Total" ? <>{
                <>
                    <p className="mt-4 text-center text-2xl">{value} slices</p>
                    <p className="mt-2 text-center text-2xl">{Math.ceil(value / 8)} Pizzas</p>
                </>
            }</> : <p className="mt-8 text-center text-2xl">{value} slices</p>}
        </div>
    );
}

export default DataCard