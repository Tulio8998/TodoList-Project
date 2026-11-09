import test from 'node:test';
import assert from 'node:assert/strict';
import { Tarefa } from './Tarefa.js';

globalThis.localStorage = {
    getItem() {
        return '[]';
    }
};

test('parsePrioridade accepts only one digit from 1 to 5', () => {
    for (const prioridade of ['1', '2', '3', '4', '5']) {
        assert.equal(Tarefa.parsePrioridade(prioridade), Number(prioridade));
    }
});

test('parsePrioridade rejects empty, out-of-range, and malformed values', () => {
    for (const prioridade of ['', '0', '6', '01', '+1', '1.0', '1a', ' 1', '1 ', 1, null]) {
        assert.throws(
            () => Tarefa.parsePrioridade(prioridade),
            { message: 'Prioridade inválida. Digite um único número entre 1 e 5.' }
        );
    }
});

test('Tarefa stores valid priority as a number', () => {
    const tarefa = new Tarefa('Tarefa', 'Descrição', '2026-12-01', 'Estudo', '3', 'TODO', 'user@example.com');

    assert.equal(tarefa.prioridade, 3);
});

test('Tarefa rejects invalid priority during construction', () => {
    assert.throws(
        () => new Tarefa('Tarefa', 'Descrição', '2026-12-01', 'Estudo', '03', 'TODO', 'user@example.com'),
        { message: 'Prioridade inválida. Digite um único número entre 1 e 5.' }
    );
});

test('parseDataTermino accepts today and future ISO dates', () => {
    const today = new Date();
    const localDate = [today.getFullYear(), String(today.getMonth() + 1).padStart(2, '0'), String(today.getDate()).padStart(2, '0')].join('-');

    assert.equal(Tarefa.parseDataTermino(localDate), localDate);
    assert.equal(Tarefa.parseDataTermino('9999-12-31'), '9999-12-31');
});

test('parseDataTermino rejects malformed date formats', () => {
    for (const data of ['', '09/11/2026', '2026-1-09', '2026/11/09', null, 20261109]) {
        assert.throws(
            () => Tarefa.parseDataTermino(data),
            { message: 'Data de término inválida. Use o formato YYYY-MM-DD.' }
        );
    }
});

test('parseDataTermino rejects dates that do not exist in the calendar', () => {
    for (const data of ['2026-02-30', '2026-13-01', '2026-00-10', '2026-04-31']) {
        assert.throws(
            () => Tarefa.parseDataTermino(data),
            { message: 'Data de término inválida. Informe uma data existente no formato YYYY-MM-DD.' }
        );
    }
});

test('parseDataTermino rejects a date before today', () => {
    const yesterday = new Date();
    yesterday.setDate(yesterday.getDate() - 1);
    const dateString = [yesterday.getFullYear(), String(yesterday.getMonth() + 1).padStart(2, '0'), String(yesterday.getDate()).padStart(2, '0')].join('-');

    assert.throws(
        () => Tarefa.parseDataTermino(dateString),
        { message: 'Data de término deve ser hoje ou uma data futura.' }
    );
});

test('Tarefa rejects an invalid due date during construction', () => {
    assert.throws(
        () => new Tarefa('Tarefa', 'Descrição', '2026-02-30', 'Estudo', '3', 'TODO', 'user@example.com'),
        { message: 'Data de término inválida. Informe uma data existente no formato YYYY-MM-DD.' }
    );
});

test('Tarefa rejects a past due date during construction', () => {
    const yesterday = new Date();
    yesterday.setDate(yesterday.getDate() - 1);
    const dateString = [yesterday.getFullYear(), String(yesterday.getMonth() + 1).padStart(2, '0'), String(yesterday.getDate()).padStart(2, '0')].join('-');

    assert.throws(
        () => new Tarefa('Tarefa', 'Descrição', dateString, 'Estudo', '3', 'TODO', 'user@example.com'),
        { message: 'Data de término deve ser hoje ou uma data futura.' }
    );
});
