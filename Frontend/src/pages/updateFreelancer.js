import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import HomeClient from "../api/homeClient";

class UpdateFreelancer extends baseClass {

constructor() {
    super();
    this.bindClassMethods(['renderFreelancer', 'onUpdate'], this);
    this.datastore = new DataStore();
}

async mount() {
    document.getElementById('update-freelancer-form').addEventListener('submit', this.onUpdate);
    this.client = new HomeClient();
    this.datastore.addChangeListener(this.renderFreelancer)
}

/**-----------------------  Render Method  -------------------------**/
async renderFreelancer() {
    const freelancer = this.dataStore.get("updatedFreelancer");

    if(freelancer) {
        this.createFreelancerDiv(freelancer);
    }
}

/**-----------------------  Event Handler  -------------------------**/
async onUpdate(event) {
    event.preventDefault();
    this.dataStore.set("updatedFreelancer", null);

    let id = document.getElementById("freelancer-id").value
    let name = document.getElementById("freelancer-name").value
    let expertise = document.getElementById("freelancer-expertise").value
    let rate = document.getElementById("freelancer-rate").value
    let location = document.getElementById("freelancer-location").value
    let contact = document.getElementById("freelancer-contact").value

    let request = {};
    request.id = id;
    request.name = name;
    request.expertise = expertise;
    request.rate = rate;
    request.location = location;
    request.contact = contact;

    const updatedFreelancer = await this.client.updateFreelancer(request, this.errorHandler);
    this.dataStore.set("updatedFreelancer", updatedFreelancer);

    if (updatedFreelancer) {
        this.showMessage(`Update success!`);
    } else {
        this.errorHandler(`Error updating. Please ensure that user ${updatedFreelancer.name} and id ${updatedFreelancer.id} exists.`);
    }
    document.getElementById('container').classList.add('show');
}

/**
 *  Main method to run when the page contents have loaded.
 **/
const main = async () => {
    const updateFreelancer = new UpdateFreelancer();
    await updateFreelancer.mount();
};
window.addEventListener('DOMContentLoaded', main);
}