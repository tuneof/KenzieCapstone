import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import HomeClient from "../api/homeClient";

class GetFreelancer extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['renderFreelancerDetails', 'onGet'], this);
        this.dataStore = new DataStore();
    }

    async mount() {
        document.getElementById('get-freelancer-details-form').addEventListener('submit', this.onGet);
        this.client = new HomeClient();

        this.dataStore.addChangeListener(this.renderFreelancerDetails)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderFreelancerDetails() {
        const freelancer = this.dataStore.get("freelancer");
        const hireStatus = this.dataStore.get("hireStatus");

        if (freelancer) {
            this.createFreelancerDiv(freelancer, hireStatus);
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onGet(event) {
        event.preventDefault();

        let id = document.getElementById("get-freelancer-details-id").value;
        this.dataStore.set("freelancer", null);

        let result = await this.client.findFreelancerById(id, this.errorHandler);
        this.dataStore.set("freelancer", result);

        let result2 = await this.client.getFreelancerHireStatus(id, this.errorHandler);
        this.dataStore.set("hireStatus", result2);

        if (!result) {
            this.errorHandler("Error doing GET! Try again...");
            document.getElementById('get-freelancer-details-id').value = "";
            return;
        }

        document.getElementById('get-freelancer-details-id').value = "";

        const container = document.getElementById('container');
        container.classList.add('show');
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const getFreelancer = new GetFreelancer();
    await getFreelancer.mount();
};

window.addEventListener('DOMContentLoaded', main);