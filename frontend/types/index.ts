import { MouseEventHandler } from "react";

export interface CustomButtonProps {
    title: string;
    containerStyles?: string;
    handleClick?: MouseEventHandler<HTMLButtonElement>
}

export interface CustomInputProps {
    id: string;
    containerStyles?: string;
}

export interface DataCardProps {
    title: string;
    value: number;
    containerStyles?: string;
}

export interface DataRibbonProps {
    total: number;
    meat: number;
    veggie: number;
    vegan: number;
}

export interface DashboardCharts {
    meat: number;
    veggie: number;
    vegan: number;
}

export interface User {
    name: string;
    lastName: string;
    created: number;
    uuid: string;
}

export interface UserPage {
    user: User[];
}

export interface Pizza {
    type: string;
    slices: number;
}

export interface Friday {
    date: number;
    pizzas: Pizza[];
}

export interface PizzaLastFridays {
    fridays: Friday[] | undefined;
}