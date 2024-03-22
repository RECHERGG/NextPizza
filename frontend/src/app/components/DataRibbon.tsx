import { DataRibbonProps } from "../../../types";
import DataCard from "./DataCard";
import DateDropdownHandler from "./DateDropdownHandler";

//TODO
function DataRibbon({total, meat, veggie, vegan}: DataRibbonProps) {

    return (
        <div className="mt-6 ml-6">
            <h1 className="text-2xl font-semibold">Dashboard</h1>
            <DateDropdownHandler />
            <div className="grid grid-cols-4 gap-32 mt-24">
                <DataCard title="Total" value={total} />
                <DataCard title="MEAT" value={meat} />
                <DataCard title="VEGGIE" value={veggie} />
                <DataCard title="VEGAN" value={vegan} />
            </div>
        </div>
    );
}

export default DataRibbon