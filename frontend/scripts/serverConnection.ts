'use server';

import axios from "axios";
import { setTokenCookie } from "../server/server";

export const instance = axios.create({
    baseURL: "http://localhost:8080"
});

export async function authenticate(name: string, lastName: string, password: string): Promise<number> {
    return await instance.post("/api/v1/login/", {
        name: name,
        lastName: lastName,
        password: password
    }).then(function (response) {
        setTokenCookie(response.data)
        return response.status;
    }).catch(function (error) {
        return error.status;
    });
}

export async function getSession(token: string | undefined): Promise<JSON> {
    return await fetch("http://localhost:8080/api/v1/session/", {
        headers: {
            "Authorization": "Bearer " + token
        }
    }).then(function (response) {
        return response.json();
    }).then(data => {
        return data.data
    }).catch(function (error) {
        return error.status;
    });
}

export async function submitRequest(uuid: string | undefined, meatSlices: string, veggieSlices: string, veganSlices: string, token: string | undefined): Promise<number> {
    return await instance.post("/api/v1/pizza/", {
        uuid: uuid,
        pizzas: [{
            type: "MEAT",
            slices: meatSlices
        }, {
            type: "VEGGIE",
            slices: veggieSlices
        }, {
            type: "VEGAN",
            slices: veganSlices
        }]
    }, {
        headers: {
            "Authorization": "Bearer " + token 
        }
    }
    ).then(function (response) {
        return response.status;
    }).catch(function (error) {
        return error.status;
    });
}

export async function getSlices(date: string, token: string | undefined) {
    return await instance.get("/api/v1/pizza/", {
        data: {
            date: date
        },
        headers: {
            "Authorization": "Bearer " + token
        }
    }).then(function (response) {
        return response.data;   
    }).catch(function (error) {
        return error.status;
    });
}

export async function getPizzaLastFridays(token: string | undefined) {
    return await instance.get("/api/v1/pizza-last-fridays/", {
        headers: {
            "Authorization": "Bearer " + token 
        }
    })
    .then(function (response) {
        return response.data;
    }).catch(function (error) {
        return error.status;
    });
}

export async function getLastFridays(token: string | undefined) {
    return await instance.get("/api/v1/last-fridays/", {
        headers: {
            "Authorization": "Bearer " + token 
        }
    })
    .then(function (response) {
        return response.data;
    }).catch(function (error) {
        return error.status;
    });
}

export async function getNextFriday(token: string | undefined) {
    return await instance.get("/api/v1/next-friday/", {
        headers: {
            "Authorization": "Bearer " + token 
        }
    })
    .then(function (response) {
        return response.data;
    }).catch(function (error) {
        return error.status;
    });
}

export async function getUsers(token: string | undefined) {
    return await instance.get("/api/v1/users/", {
        headers: {
            "Authorization": "Bearer " + token
        }
    }).then(function (response) {
        return response.data;
    }).catch(function (error) {
        return error.status;
    });
}

export async function createUser(name: string, lastName: string, token: string | undefined) {
    return await instance.post("/api/v1/user/", {
        name: name,
        lastName: lastName
    }, {
        headers: {
            "Authorization": "Bearer " + token 
        }
    }).then(function (response) {
        return response.data;
    }).catch(function (error) {
        return error.status;
    });
}

export async function editUser(name: string, lastName: string, uuid: string, token: string | undefined) {
    return await instance.post("/api/v1/edit/", {
        name: name,
        lastName: lastName,
        uuid: uuid
    }, {
        headers: {
            "Authorization": "Bearer " + token 
        }
    }).then(function (response) {
        return response.data;
    }).catch(function (error) {
        return error.status;
    });
}

export async function editUserWithPassword(name: string, lastName: string, password: string, uuid: string | undefined, token: string | undefined) {
    return await instance.post("/api/v1/edit/", {
        name: name,
        lastName: lastName,
        password: password,
        uuid: uuid
    }, {
        headers: {
            "Authorization": "Bearer " + token 
        }
    }).then(function (response) {
        return response.data;
    }).catch(function (error) {
        console.log(error)
        return error.status;
    });
}