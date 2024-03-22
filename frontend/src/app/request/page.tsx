"use client";

import { useState } from "react";
import { request } from "../../../server/server";

export default function Request() { 

    const [meatValue, setMeatValue] = useState("0");
    const [veggieValue, setVeggieValue] = useState("0");
    const [veganValue, setVeganValue] = useState("0");

    async function submit() {
      var status = await request(meatValue, veggieValue, veganValue);

      if (status === 200) return window.location.replace("/cp/dashboard");
    }

    return(
        <section className="bg-gray-50 dark:bg-gray-900">
      <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
        <a href="#" className="flex items-center mb-6 text-2xl font-semibold text-gray-900 dark:text-white">
            <img className="w-8 h-8 mr-2" src="./favicon.ico" alt="logo" />
            NextPizza
        </a>
        <div className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
            <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
                    Configur your Lunch ({parseInt(meatValue) + parseInt(veggieValue) + parseInt(veganValue)} slices)
                </h1>
                <div className="space-y-4 md:space-y-6">
                    <div>
                      <label htmlFor="steps-range" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">MEAT</label>
                      <input id="steps-range"
                      type="range"
                        min="0" max="20" step="1"
                        value={meatValue}
                        onInput={(event) => {
                          setMeatValue((event.target as HTMLTextAreaElement).value)
                        }}
                        className="w-full h-2 bg-gray-200 rounded-lg appearance-none cursor-pointer dark:bg-gray-700" />

                      <div className="flex justify-between text-gray-500">
                        <span>{meatValue}</span>
                        <span>20 slices</span>
                      </div>
                    </div>

                    <div>
                      <label htmlFor="steps-range" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">VEGGIE</label>
                      <input id="steps-range"
                      type="range"
                        min="0" max="20" step="1"
                        value={veggieValue}
                        onInput={(event) => {
                          setVeggieValue((event.target as HTMLTextAreaElement).value)
                        }}
                        className="w-full h-2 bg-gray-200 rounded-lg appearance-none cursor-pointer dark:bg-gray-700" />

                      <div className="flex justify-between text-gray-500">
                        <span>{veggieValue}</span>
                        <span>20 slices</span>
                      </div>
                    </div>

                    <div>
                      <label htmlFor="steps-range" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">VEGAN</label>
                      <input id="steps-range"
                      type="range"
                        min="0" max="20" step="1"
                        value={veganValue}
                        onInput={(event) => {
                          setVeganValue((event.target as HTMLTextAreaElement).value)
                        }}
                        className="w-full h-2 bg-gray-200 rounded-lg appearance-none cursor-pointer dark:bg-gray-700" />

                      <div className="flex justify-between text-gray-500">
                        <span>{veganValue}</span>
                        <span>20 slices</span>
                      </div>
                    </div>
                    
                    <button type="submit" 
                    className="w-full text-white bg-blue-600 hover:bg-blue-700
                     focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg
                      text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700
                       dark:focus:ring-primary-800" onClick={submit}>Enter</button>
                </div>
          </div>
        </div>
    </div>
  </section>
    )
}