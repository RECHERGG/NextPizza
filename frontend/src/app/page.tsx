"use client";

import { authenticate } from "../../scripts/serverConnection";


export default function Home() {

  const delay = (ms: number | undefined) => new Promise(res => setTimeout(res, ms));

  const handleLogin = async () => {
    const name = (document.getElementById("name") as HTMLInputElement).value;
    const lastName = (document.getElementById("lastName") as HTMLInputElement).value;
    const password = (document.getElementById("password") as HTMLInputElement).value;
    if (name === null) return;
    if (lastName === null) return;
    if (password === null) return;

    const status = await authenticate(name, lastName, password);
    if (status !== 200) return;

    await delay(500);
    window.location.replace("/request");
  }

  return (
    <section className="bg-gray-50 dark:bg-gray-900">
      <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
        <a href="./" className="flex items-center mb-6 text-2xl font-semibold text-gray-900 dark:text-white">
            <img className="w-8 h-8 mr-2" src="./favicon.ico" alt="logo" />
            NextPizza    
        </a>
        <div className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
            <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
                    Log in
                </h1>
                <div className="space-y-4 md:space-y-6">
                    <div>
                        <label htmlFor="text" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your Name</label>
                        <input type="text" name="name" id="name" className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Max" />
                    </div>

                    <div>
                        <label htmlFor="text" 
                        className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your LastName</label>
                        <input type="text"
                         name="lastName" 
                         id="lastName" 
                         placeholder="Mustermann" 
                         className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm 
                         rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5
                          dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400
                           dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" />
                    </div>

                    <div>
                        <label htmlFor="text" 
                        className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Password</label>
                        <input type="password"
                         name="password"
                         id="password" 
                         placeholder="••••••••" 
                         className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm 
                         rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5
                          dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400
                           dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" />
                    </div>

                    <button type="submit" 
                    className="w-full text-white bg-blue-600 hover:bg-blue-700
                     focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg
                      text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700
                       dark:focus:ring-primary-800"
                    onClick={handleLogin}>Login</button>
                </div>
          </div>
        </div>
    </div>
  </section>
  );
}