"use server";

import { cookies } from "next/headers";
import { submitRequest, getUsers, createUser, getPizzaLastFridays, getNextFriday, getLastFridays, getSlices, editUser, editUserWithPassword } from "../scripts/serverConnection";

export const setTokenCookie = async (data: any) => {
    cookies().set({
        name: "token",
        value: data.token,
        httpOnly: true,
        path: "/"
    })
}

export const setUUIDCookie = async (data: any) => {
    cookies().set({
        name: "uuid",
        value: data.uuid,
        httpOnly: true,
        path: "/"
    })
}

export const request = async (meatSlices: string, veggieSlices: string, veganSlices: string): Promise<number> => {
    return await submitRequest(cookies().get("uuid")?.value, meatSlices, veggieSlices, veganSlices, cookies().get("token")?.value);
}

export const slices = async (date: string) => {
    return await getSlices(date, cookies().get("token")?.value);
}

export const users = async () => {
    return await getUsers(cookies().get("token")?.value);
}

export const createNewUser = async (name: string, lastName: string) => {
    return await createUser(name, lastName, cookies().get("token")?.value);
}

export const editUserWithName = async (name: string, lastName: string, uuid: string) => {
    return await editUser(name, lastName, uuid, cookies().get("token")?.value);
}

export const editUserPassword = async (name: string, lastName: string, password: string, uuid: string) => {
    return await editUserWithPassword(name, lastName, password, uuid, cookies().get("token")?.value);
}

export const pizzaLastFridays = async () => {
    return await getPizzaLastFridays(cookies().get("token")?.value);
}

export const nextFriday = async () => {
    return await getNextFriday(cookies().get("token")?.value);
}

export const lastFridays = async () => {
    return await getLastFridays(cookies().get("token")?.value);
}