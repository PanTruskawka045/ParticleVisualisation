import './App.css';
import {useEffect, useState} from "react";
import {CopyToClipboard} from "react-copy-to-clipboard/src";

function App() {

    const [offset, setOffset] = useState({
        x: 0.0,
        y: 0.0,
        z: 0.0
    });
    const [particle, setParticle] = useState("");
    const [availableParticles, setAvailableParticles] = useState([]);
    const [loading, setLoading] = useState(true);
    const [extraOptions, setExtraOptions] = useState({});
    const [count, setCount] = useState(0);
    const [speed, setSpeed] = useState(0);

    useEffect(() => {
        (async () => {
            await fetch("/partices.json").then(res => res.json()).then(data => {
                setAvailableParticles(data);
                setParticle(Object.keys(data)[0]);
            });
            setLoading(false);
        })();
    }, []);

    const submit = () => {
        (async () => {
            await fetch("/api/particle", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    particle: particle,
                    extraOptions: extraOptions,
                    offset: offset,
                    count: count,
                    speed: speed
                })
            }).then(res => res.json()).then(data => {
                console.log(data);
            })
        })();
    }

    if (loading) {
        return (<div className={"px-[25%] py-[80px] flex flex-col justify-center items-center"}>
            <p>Loading...</p>
        </div>)
    }

    let options = `ParticleTypes.${particle}`;
    let currentParticleData = availableParticles[`${particle}`];

    if (currentParticleData.optionsConstructor) {

        options = currentParticleData.optionsConstructor;

        if (currentParticleData.fields) {
            Object.keys(currentParticleData.fields).forEach((key) => {
                extraOptions[key] = extraOptions[key] || 0;
            });
        }

        Object.keys(extraOptions).forEach((key) => {
            options = options.replace(`%${key}%`, `${extraOptions[key]}`.replace("#", ""));
        });
    }

    let packetCode = `new ClientboundLevelParticlesPacket(${options}, true, x, y, z, ${offset.x}f, ${offset.y}f, ${offset.z}f, ${speed}f, ${count})`

    return (
        <form className={"px-[25%] py-[80px] flex flex-col"} onSubmit={(e) => {
            e.preventDefault()
        }}>
            <label>X Offset:</label>
            <input type={"range"} step={0.1} max={10} value={offset.x}
                   onChange={(e) => setOffset({...offset, x: +e.target.value})}/>
            <label>Y Offset:</label>
            <input type="range" step={0.1} max={10} value={offset.y}
                   onChange={(e) => setOffset({...offset, y: +e.target.value})}/>
            <label>Z Offset:</label>
            <input type="range" step={0.1} max={10} value={offset.z}
                   onChange={(e) => setOffset({...offset, z: +e.target.value})}/>
            <p>Offset: {JSON.stringify(offset)}</p>

            <label>Speed: {speed}</label>
            <input type="range" step={0.1} max={10} value={speed} onChange={(e) => setSpeed(+e.target.value)}/>
            <label>
                Count: {count}
            </label>
            <input type="range" value={count} onChange={(e) => setCount(+e.target.value)}/>

            <select value={particle} onChange={(e) => {
                setParticle(e.target.value);
                setExtraOptions({})
            }}>
                {Object.keys(availableParticles).map((p) => <option key={p} value={p}>{p}</option>)}
            </select>

            <ParticleOptions particle={particle} particleData={currentParticleData}
                             extraOptions={extraOptions}
                             setExtraOptions={setExtraOptions}/>


            <pre className={"text-wrap"}>
                {packetCode}
            </pre>

            <div className={"flex gap-2 items-center justify-center"}>
                {/*Looks disgusting but it's better than floating text*/}
                <button type={"button"} className={"p-1 bg-indigo-500 rounded text-white hover:scale-105 hover:transform duration-300"}
                        onClick={submit}>Update</button>
                <CopyToClipboard text={packetCode}>
                    <button type={"button"} className={"p-1 bg-indigo-500 rounded text-white hover:scale-105 hover:transform duration-300"} onClick={submit}>Copy to
                        clipboard
                    </button>
                </CopyToClipboard>
            </div>

        </form>
    );
}

function ParticleOptions({particleData, extraOptions, setExtraOptions}) {

    if (!particleData.fields) {
        return (<></>);
    }
    return (
        <>
            {Object.keys(particleData.fields).map((field) => {
                let fieldData = particleData.fields[field];

                switch (fieldData.type) {
                    case "float": {
                        return (<>
                            <label>{field}: {extraOptions[field]}</label>
                            <input type="range" step={0.1} max={10} value={extraOptions[field] || 0}
                                   onChange={(e) => {
                                       let extra = {...extraOptions} || {};
                                       extra[field] = e.target.value;
                                       setExtraOptions(extra);
                                   }}/>
                        </>)
                    }
                    case "int": {
                        return (<>
                            <label>{field}: {extraOptions[field]}</label>
                            <input type={"number"} value={extraOptions[field] || 0}
                                   onChange={(e) => {
                                       let extra = {...extraOptions} || {};
                                       extra[field] = e.target.value;
                                       setExtraOptions(extra);
                                   }}/>
                        </>)
                    }
                    case "color": {
                        return (<>
                            <label>{field}: {extraOptions[field]}</label>
                            <input type={"color"} value={extraOptions[field] || "#000000"}
                                   onChange={(e) => {
                                       let extra = {...extraOptions} || {};
                                       extra[field] = e.target.value;
                                       setExtraOptions(extra);
                                   }}/>
                        </>)
                    }

                }

                return (
                    <div>Invalid type {fieldData.type}</div>

                )
            })}
        </>
    );
}

export default App;
