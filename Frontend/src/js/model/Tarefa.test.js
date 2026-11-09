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
