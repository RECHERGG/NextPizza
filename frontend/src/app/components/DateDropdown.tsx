"use client";

import { useEffect, useState } from "react";
import { lastFridays, nextFriday } from "../../../server/server";
import { setRequestDate } from "../cp/dashboard/page";
import { useSearchParams } from "next/navigation";

export default function DateDropdown() {
    const searchParams = useSearchParams();
    const delay = (ms: number | undefined) => new Promise(res => setTimeout(res, ms));

    const [dropDown, setdropDown] = useState<boolean>();
    const [date, setDate] = useState<string>();
    const [value, setValue] = useState<string | null>(null);

    const [items, setItems] = useState<{value: string | undefined, label: string | undefined}[]>();

    function toggleDropDown() {
        setdropDown(!dropDown);
    }

    async function getNextFriday() {
        let data = await nextFriday();
        const friday = data.data.friday;
        
        setDate(friday);
        return friday;
    }

    async function getLastFridays() {
        let data = await lastFridays();
        const fr: string[] = data.data.fridays;

        return fr;
    }

    async function request() {
        const fr = await getLastFridays();
        const nextFr = await getNextFriday();

        let i: {value: string | undefined, label: string | undefined}[] = [];
        
        fr.forEach(friday => {
            i.push({value: new Date(friday).toLocaleDateString(), label: new Date(friday).toLocaleDateString()});
        })
        
        i.push({ value: new Date(nextFr).toLocaleDateString(), label: new Date(nextFr).toLocaleDateString() });
        setItems(i);
    }

    useEffect(() => {
        request();
    }, [])

    if (value === null) {
        setValue(searchParams.get("date") ? searchParams.get("date") : (date !== undefined ? new Date(date).toLocaleDateString() : ""))
    }

    function handelUpdate(e: any) {
        if (!dropDown || value === null) return;

        setValue(e.target.value);
        setRequestDate(e.target.value);
    }

    delay(10000);

    return (
        <div className="mt-2 fixed">
            <button onClick={toggleDropDown} className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800" type="button">{searchParams.get("date") ?? value} <svg className="w-2.5 h-2.5 ms-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
            </svg>
            </button>

            <div id="dropdownDefaultRadio" className={`z-10 ${!dropDown ? "hidden" : "visibil"} w-48 bg-white divide-y divide-gray-100 rounded-lg shadow dark:bg-gray-700 dark:divide-gray-600`}>
                <ul className="mt-2 p-3 space-y-3 text-sm text-gray-700 dark:text-gray-200" aria-labelledby="dropdownRadioButton">

                    <>{items != undefined ? items.map(item => {
                        return <li key={item.value}>
                            <div className="flex items-center">
                                <input id={item.value} type="radio" value={item.value} checked={value === item.value} onChange={e => handelUpdate(e)} name="Date" className="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500" />
                                <label htmlFor={item.value} className="ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">{item.label}</label>
                            </div>
                        </li>
                    }) : ""}</>
                </ul>
            </div>
        </div>
    )
}