"use client";

import { CustomInputProps } from "../../../types";

const CustomInput = ({ id, containerStyles }: CustomInputProps) => {
    return (
        <input
            id={id}
            type="text"
            className={`custom-input ${containerStyles}`}>
            </input>
    );
}

export default CustomInput