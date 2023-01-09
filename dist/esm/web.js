import { WebPlugin } from '@capacitor/core';
import { OptionsRequiredError } from './utils/errors';
export class BluetoothSerialWeb extends WebPlugin {
    async isEnabled() {
        throw new Error('Method not implemented.');
    }
    async enable() {
        throw new Error('Method not implemented.');
    }
    async scan() {
        throw new Error('Method not implemented.');
    }
    async getPairedDevices() {
        throw new Error('Method not implemented.');
    }
    async connect(options) {
        if (!options) {
            return Promise.reject(new OptionsRequiredError());
        }
        throw new Error('Method not implemented.');
    }
    async connectInsecure(options) {
        if (!options) {
            return Promise.reject(new OptionsRequiredError());
        }
        throw new Error('Method not implemented.');
    }
    async disconnect(options) {
        if (!options) {
            return Promise.reject(new OptionsRequiredError());
        }
        throw new Error('Method not implemented.');
    }
    async isConnected(options) {
        if (!options) {
            return Promise.reject(new OptionsRequiredError());
        }
        throw new Error('Method not implemented.');
    }
    async read(options) {
        if (!options) {
            return Promise.reject(new OptionsRequiredError());
        }
        throw new Error('Method not implemented.');
    }
    async readUntil(options) {
        if (!options) {
            return Promise.reject(new OptionsRequiredError());
        }
        throw new Error('Method not implemented.');
    }
    async write(options) {
        if (!options) {
            return Promise.reject(new OptionsRequiredError());
        }
        throw new Error('Method not implemented.');
    }
    async enableNotifications(options) {
        if (!options) {
            return Promise.reject(new OptionsRequiredError());
        }
        throw new Error('Method not implemented.');
    }
    async disableNotifications(options) {
        if (!options) {
            return Promise.reject(new OptionsRequiredError());
        }
        throw new Error('Method not implemented.');
    }
}
//# sourceMappingURL=web.js.map