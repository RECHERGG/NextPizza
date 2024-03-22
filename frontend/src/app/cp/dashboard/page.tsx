"use client";

import DataRibbon from "@/app/components/DataRibbon";
import DashboardPieChart from "@/app/components/DashboardPieChart";
import DashboardAreaChart from "@/app/components/DashboardAreaChart";
import { nextFriday, pizzaLastFridays, slices } from "../../../../server/server";
import { Friday } from "../../../../types";
import { useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";

export type Pizza = {
    type: string;
    slices: number;
}

export type User = {
    pizzas: Pizza[];
    uuid: string;
}

export function setRequestDate(date: string) {
    window.location.replace("/cp/dashboard" + "?date=" + date);
}


export default function Request() {
    const searchParams = useSearchParams();
    let requestDate = searchParams.get("date") ?? "";

    async function getData() {
        if (requestDate === "") {
            const data = await nextFriday();
            setRequestDate(new Date(data.data.friday).toLocaleDateString())
        }
        let data = await slices(requestDate);
        if (data === undefined || data.data === undefined) return null;
        const users: User[] = data.data.users;

        return users;
    }

    async function getPizzaFridays() {
        let data = await pizzaLastFridays();
        const fridays: Friday[] = data.data.fridays;

        return fridays;
    }

    const [meatValue, setMeatValue] = useState<number>(0);
    const [veggieValue, setVeggieValue] = useState<number>(0);
    const [veganValue, setVeganValue] = useState<number>(0);

    const [fridays, setFridays] = useState<Friday[]>();

    async function handle() {
        const users = await getData();
        const fr: Friday[] = await getPizzaFridays();

        setFridays(fr);

        let meat = meatValue;
        let veggie = veggieValue;
        let vegan = veganValue;

        if (users === null) return;
        users.forEach((user: User) => {
            user.pizzas.map((pizza) => {
                if (pizza.type === "MEAT") {
                    meat += pizza.slices;
                    setMeatValue(meat);
                }

                if (pizza.type === "VEGGIE") {
                    veggie += pizza.slices;
                    setVeggieValue(veggie);
                }

                if (pizza.type === "VEGAN") {
                    vegan += pizza.slices;
                    setVeganValue(vegan);
                }
            })
        });
    }

    useEffect(() => {
        handle();
    }, [])

    return (
        <div className="fixed">
            <DataRibbon total={meatValue + veggieValue + veganValue} meat={meatValue} veggie={veggieValue} vegan={veganValue} />
            <div className="flex">
                <DashboardPieChart meat={meatValue} veggie={veggieValue} vegan={veganValue} />
                <DashboardAreaChart fridays={fridays} />
            </div>
        </div>
    )
}