const units = {
  Celcius: "°C",
  Fahrenheit: "°F",
  absoluteHumidity:"%"};


const config = {
  minTemp: -20,
  maxTemp: 50,
  unit: "Celcius" };

const config1 = {
  minTemp: 0,
  maxTemp: 100,
  unit: "absoluteHumidity" };


// Change min and max temperature values

const tempValueInputs = document.querySelectorAll("input[type='text']");

tempValueInputs.forEach(input => {
  input.addEventListener("change", event => {
    const newValue = event.target.value;

    if (isNaN(newValue)) {
      return input.value = config[input.id];
    } else {
      config[input.id] = input.value;
      range[input.id.slice(0, 3)] = config[input.id]; // Update range
      return setTemperature(); // Update temperature
    }
  });
});

// Switch unit of temperature

const unitP = document.getElementById("unit");

unitP.addEventListener("click", () => {
  config.unit = config.unit === "Celcius" ? "Fahrenheit" : "Celcius";
  unitP.innerHTML = config.unit + ' ' + units[config.unit];
  return setTemperature();
});



// Change temperature

const range = document.querySelector("input[type='range']");
const temperature = document.getElementById("temperature");
const temperature1 = document.getElementById("temperature1");



function setTemperature(v) {
  if(v){
    temperature.style.height = (v - config.minTemp) / (config.maxTemp - config.minTemp) * 100 + "%";
    temperature.dataset.value = v + units[config.unit];
  }
  else {
    temperature.style.height = (range.value - config.minTemp) / (config.maxTemp - config.minTemp) * 100 + "%";
    temperature.dataset.value = range.value + units[config.unit];
  }
}

function setTemperature1(v) {
  if(v){
    var h = (v - config1.minTemp) / (config1.maxTemp - config1.minTemp) * 100 + "%";
    temperature1.style.height = (v - config1.minTemp) / (config1.maxTemp - config1.minTemp) * 100 + "%";
    temperature1.dataset.value = v + units[config1.unit];
  }
  else {
    temperature1.style.height = (range.value - config1.minTemp) / (config1.maxTemp - config1.minTemp) * 100 + "%";
    temperature1.dataset.value = range.value + units[config1.unit];
  }
}



range.addEventListener("input", setTemperature);
